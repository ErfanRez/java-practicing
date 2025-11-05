package com.danacup.service;

import com.danacup.dto.cart.AddShoppingCartItemRequestDto;
import com.danacup.dto.cart.ShoppingCartDto;
import com.danacup.dto.cart.ShoppingCartItemDto;
import com.danacup.dto.cart.UpdateShoppingCartItemRequestDto;
import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.SubmitOrderItemRequestDto;
import com.danacup.dto.order.SubmitOrderRequestDto;
import com.danacup.entity.*;
import com.danacup.exception.ApiError;
import com.danacup.mapper.ShoppingCartMapper;
import com.danacup.repository.ShoppingCartEntityRepository;
import com.danacup.repository.ShoppingCartItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
    private final ShoppingCartEntityRepository shoppingCartRepository;
    private final ShoppingCartItemEntityRepository shoppingCartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public ShoppingCartEntity findOrCreateShoppingCart(Long userId) throws ResponseStatusException {
        var user = userService.findById(userId);
        var existsShoppingCart = this.findShoppingCartByUserId(userId);
        return existsShoppingCart.orElseGet(() -> this.saveShoppingCart(
                ShoppingCartEntity.builder()
                        .items(Collections.emptyList())
                        .user(user)
                        .build()
        ));
    }

    /**
     * Make new shopping cart item for user
     *
     * @param requestDto
     * @param userId
     * @return new maked shopping cart item details
     * @throws ResponseStatusException
     */
    @Transactional
    public ShoppingCartItemDto addShoppingCartItem(AddShoppingCartItemRequestDto requestDto, Long userId) throws ResponseStatusException {
        var shoppingCart = this.findOrCreateShoppingCart(userId);
        var product = productService.findById(requestDto.productId);
        if (requestDto.quantity > product.getUsableBalance()) {
            throw new ApiError.BadRequest("invalid-shopping-cart-item-quantity");
        }
        var shoppingCartItem = this.saveShoppingCartItem(
                ShoppingCartItemEntity.builder()
                        .shoppingCart(shoppingCart)
                        .product(product)
                        .quantity(requestDto.quantity)
                        .build()
        );
        logger.info("New shoppingCartItem created with id: {}", shoppingCartItem.getId());
        return shoppingCartMapper.toDto(shoppingCartItem);
    }

    /**
     * Update new shopping cart item for user
     *
     * @param requestDto
     * @param userId
     * @return new maked shopping cart item details
     * @throws ResponseStatusException
     */
    @Transactional
    public ShoppingCartItemDto updateShoppingCartItem(Long itemId, UpdateShoppingCartItemRequestDto requestDto, Long userId) throws ResponseStatusException {
        var shoppingCart = this.findOrCreateShoppingCart(userId);
        var shoppingCartItem = this.findItemById(itemId);
        this.assertShoppingCartItem(shoppingCartItem, shoppingCart);
        if (requestDto.quantity > shoppingCartItem.getProduct().getUsableBalance()) {
            throw new ApiError.BadRequest("invalid-shopping-cart-item-quantity");
        }
        shoppingCartItem.setQuantity(requestDto.quantity);
        logger.info("Update shoppingCartItem with id: {}", shoppingCartItem.getId());
        return shoppingCartMapper.toDto(shoppingCartItem);
    }

    /**
     * Delete the shopping cart item
     *
     * @param itemId
     * @param userId
     * @throws ResponseStatusException
     */
    @Transactional
    public void deleteShoppingCartItem(Long itemId, Long userId) throws ResponseStatusException {
        var shoppingCart = this.findOrCreateShoppingCart(userId);
        var shoppingCartItem = this.findItemById(itemId);
        this.assertShoppingCartItem(shoppingCartItem, shoppingCart);
        logger.info("shopping cart item deleted with itemId: {}", shoppingCartItem.getId());
        shoppingCartItemRepository.delete(shoppingCartItem);
    }

    @Transactional(readOnly = true)
    public ShoppingCartEntity findById(Long id) throws ResponseStatusException {
        return shoppingCartRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("shopping-cart-not-found"));
    }

    @Transactional(readOnly = true)
    public ShoppingCartItemEntity findItemById(Long id) throws ResponseStatusException {
        return shoppingCartItemRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("shopping-cart-item-not-found"));
    }

    @Transactional(readOnly = true)
    public Optional<ShoppingCartEntity> findShoppingCartByUserId(Long userId) throws ResponseStatusException {
        return shoppingCartRepository.findOne((r, q, b) -> {
            return b.equal(r.join(ShoppingCartEntity_.USER).get(UserEntity_.ID), userId);
        });
    }

    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCartByUserId(Long userId) throws ResponseStatusException {
        var shoppingCart = this.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new ApiError.NotFound("shopping-cart-not-found"));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartEntity saveShoppingCart(ShoppingCartEntity shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    public ShoppingCartItemEntity saveShoppingCartItem(ShoppingCartItemEntity shoppingCartItem) {
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    public void assertShoppingCartItem(ShoppingCartItemEntity shoppingCartItem, ShoppingCartEntity shoppingCart) throws ResponseStatusException {
        if (!Objects.equals(shoppingCartItem.getShoppingCart().getId(), shoppingCart.getId())) {
            throw new ApiError.Forbidden("invalid-shopping-cart-item-access");
        }
    }

}
