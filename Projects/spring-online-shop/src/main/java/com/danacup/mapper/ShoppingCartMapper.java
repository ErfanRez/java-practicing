package com.danacup.mapper;

import com.danacup.dto.cart.ShoppingCartDto;
import com.danacup.dto.cart.ShoppingCartItemDto;
import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.OrderItemDto;
import com.danacup.entity.OrderEntity;
import com.danacup.entity.OrderItemEntity;
import com.danacup.entity.ShoppingCartEntity;
import com.danacup.entity.ShoppingCartItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartMapper {

    private final ProductMapper productMapper;

    public ShoppingCartDto toDto(ShoppingCartEntity shoppingCart) {
        return ShoppingCartDto.builder()
                .id(shoppingCart.getId())
                .items(shoppingCart.getItems().stream().map(this::toDto).toList())
                .build();
    }

    public ShoppingCartItemDto toDto(ShoppingCartItemEntity shoppingCartItem) {
        return ShoppingCartItemDto.builder()
                .id(shoppingCartItem.getId())
                .product(productMapper.toDto(shoppingCartItem.getProduct()))
                .quantity(shoppingCartItem.getQuantity())
                .build();
    }


}
