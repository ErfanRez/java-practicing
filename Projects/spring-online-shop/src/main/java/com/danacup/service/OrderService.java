package com.danacup.service;

import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.SubmitOrderItemRequestDto;
import com.danacup.dto.order.SubmitOrderRequestDto;
import com.danacup.entity.*;
import com.danacup.exception.ApiError;
import com.danacup.mapper.OrderMapper;
import com.danacup.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderEntityRepository orderEntityRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final UserService userService;

    /**
     * Make new order with validate order requirements and do product transactions
     *
     * @param requestDto
     * @param userId
     * @return new maked order details
     * @throws ResponseStatusException
     */
    @Transactional
    public OrderDto submitOrder(SubmitOrderRequestDto requestDto, Long userId) throws ResponseStatusException {
        var user = userService.findById(userId);
        var items = this.validateSubmitOrder(requestDto.items);
        var totalPrice = this.getOrderTotalPrice(items);
        var order = this.saveOrder(
                OrderEntity.builder()
                        .status(OrderEntity.Status.PENDING)
                        .totalPrice(totalPrice)
                        .items(items)
                        .fullName(requestDto.fullName)
                        .address(requestDto.address)
                        .zipCode(requestDto.zipCode)
                        .user(user)
                        .build()
        );
        this.initiateOrderProductTransactions(order);
        logger.info("New order created with id: {}", order.getId());
        return orderMapper.toDto(order);
    }

    /**
     * Validate and make order items with product versioning and quantity check
     *
     * @param items
     * @return list of order item entities
     * @throws ResponseStatusException
     */
    @Transactional(readOnly = true)
    public List<OrderItemEntity> validateSubmitOrder(List<SubmitOrderItemRequestDto> items) throws ResponseStatusException {
        List<OrderItemEntity> result = new java.util.ArrayList<>(Collections.emptyList());
        var productIds = items.stream().map(SubmitOrderItemRequestDto::getProductId).toList();
        var products = productService.findByIds(productIds);
        if (products.isEmpty()) {
            throw new ApiError.Conflict("empty-product-list");
        }
        for (SubmitOrderItemRequestDto item : items) {
            var product = productService.findById(item.productId);
            productService.assertProductVersion(product, item.getProductVersion());
            if (product.getUsableBalance() < item.quantity) {
                throw new ApiError.Conflict("invalid-product-usable-quantity");
            }
            var orderItem = OrderItemEntity.builder()
                    .product(product)
                    .quantity(item.quantity)
                    .totalPrice(item.quantity * product.getPrice())
                    .build();
            result.add(orderItem);
        }
        return result;
    }

    /**
     * Take order products usable balance and add them into locked balance
     *
     * @param order
     * @throws ResponseStatusException
     */
    @Transactional
    public void initiateOrderProductTransactions(OrderEntity order) throws ResponseStatusException {
        for (OrderItemEntity item : order.getItems()) {
            productService.decreaseProductUsableBalance(item.getProduct().getId(), item.getQuantity());
            productService.increaseProductLockedBalance(item.getProduct().getId(), item.getQuantity());
        }
    }

    /**
     * Take order products usable locked balance (exit products from storaae)
     *
     * @param order
     * @throws ResponseStatusException
     */
    @Transactional
    public void completeOrderProductTransactions(OrderEntity order) throws ResponseStatusException {
        for (OrderItemEntity item : order.getItems()) {
            productService.decreaseProductLockedBalance(item.getProduct().getId(), item.getQuantity());
        }
    }

    /**
     * Return order products locked balance into usable balance (rollback)
     *
     * @param order
     * @throws ResponseStatusException
     */
    @Transactional
    public void cancelOrderProductTransactions(OrderEntity order) throws ResponseStatusException {
        for (OrderItemEntity item : order.getItems()) {
            productService.decreaseProductLockedBalance(item.getProduct().getId(), item.getQuantity());
            productService.increaseProductUsableBalance(item.getProduct().getId(), item.getQuantity());
        }
    }

    public Double getOrderTotalPrice(List<OrderItemEntity> items) {
        Double totalPrice = 0.0;
        for (OrderItemEntity item : items) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * Handle payment of order (after IPG payment callback), MVP logic
     *
     * @param id
     * @param isSuccessfully
     * @param requestUserId
     * @return OrderDto with the new state of the order
     * @throws ResponseStatusException
     */
    @Transactional
    public OrderDto handleOrderPayment(Long id, Boolean isSuccessfully, Long requestUserId) throws ResponseStatusException {
        var user = userService.findById(requestUserId);
        var order = this.findById(id);
        var orderId = order.getId();
        this.validateOrderAccessForUser(order, user);
        this.assertOrderStatus(order, OrderEntity.Status.PENDING);
        if (isSuccessfully) {
            order.setStatus(OrderEntity.Status.IN_PROGRESS);
            this.saveOrder(order);
            logger.info("order payment completed with id: {}", orderId);
            logger.info("order in-progress started with id: {}", orderId);
        } else {
            logger.info("order payment failed with id: {}", orderId);
        }
        return orderMapper.toDto(order);
    }

    /**
     * Complete the order (delivered to user) and hande product exit from store
     *
     * @param id
     * @param requestedUser
     * @return OrderDto with the new changed details
     * @throws ResponseStatusException
     */
    @Transactional
    public OrderDto completeOrder(Long id, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var order = this.findById(id);
        var orderId = order.getId();
        this.assertOrderStatus(order, OrderEntity.Status.IN_PROGRESS);
        order.setStatus(OrderEntity.Status.COMPLETED);
        order.setCompletedAt(new Date());
        this.saveOrder(order);
        this.completeOrderProductTransactions(order);
        logger.info("order fully completed with id: {}", orderId);
        return orderMapper.toDto(order);
    }

    /**
     * Cancel order and return the product usable balance
     *
     * @param id
     * @param requestedUser
     * @return OrderDto with the new changed details
     * @throws ResponseStatusException
     */
    @Transactional
    public OrderDto cancelOrder(Long id, UserEntity requestedUser) throws ResponseStatusException {
        userService.assertUserIsAdmin(requestedUser);
        var order = this.findById(id);
        this.assertOrderStatus(order, OrderEntity.Status.PENDING);
        order.setStatus(OrderEntity.Status.CANCELLED);
        order.setCancelledAt(new Date());
        this.saveOrder(order);
        this.cancelOrderProductTransactions(order);
        logger.info("order cancelled with id: {}", order.getId());
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public OrderEntity findById(Long id) throws ResponseStatusException {
        return orderEntityRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("order-not-found"));
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id, Long requestUserId) throws ResponseStatusException {
        var user = userService.findById(requestUserId);
        var order = this.findById(id);
        this.validateOrderAccessForUser(order, user);
        return orderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> filterOrders(Long userId, Pageable pageable) throws ResponseStatusException {
        var orders = orderEntityRepository.findAll((r, q, b) -> {
            return b.equal(r.join(OrderEntity_.USER).get(UserEntity_.ID), userId);
        }, pageable);
        return orders.map(orderMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() throws ResponseStatusException {
        var orders = orderEntityRepository.findAll();
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Transactional
    public OrderEntity saveOrder(OrderEntity order) {
        return orderEntityRepository.save(order);
    }

    /**
     * Check the ownership of the order with the requested user
     *
     * @param order
     * @param user
     * @throws ResponseStatusException
     */
    @Transactional(readOnly = true)
    public void validateOrderAccessForUser(OrderEntity order, UserEntity user) throws ResponseStatusException {
        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new ApiError.Forbidden("invalid-user-order-access");
        }
    }

    /**
     * Check the expected order status
     *
     * @param order
     * @param status
     * @throws ResponseStatusException
     */
    public void assertOrderStatus(OrderEntity order, OrderEntity.Status status) throws ResponseStatusException {
        if (order.getStatus() != status) {
            throw new ApiError.Conflict("invalid-order-status");
        }
    }


}
