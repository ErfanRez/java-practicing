package com.danacup.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCartDto {

    private Long id;
    private List<ShoppingCartItemDto> items;

}
