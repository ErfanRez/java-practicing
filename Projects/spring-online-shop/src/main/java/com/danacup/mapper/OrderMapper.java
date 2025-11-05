package com.danacup.mapper;

import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.OrderItemDto;
import com.danacup.dto.product.ProductDto;
import com.danacup.entity.OrderEntity;
import com.danacup.entity.OrderItemEntity;
import com.danacup.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderDto toDto(OrderEntity order) {
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .items(order.getItems().stream().map(this::toDto).toList())
                .fullName(order.getFullName())
                .address(order.getAddress())
                .zipCode(order.getZipCode())
                .build();
    }

    public OrderItemDto toDto(OrderItemEntity orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .product(productMapper.toDto(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }


}
