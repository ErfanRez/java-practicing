package com.danacup.dto.cart;

import com.danacup.dto.product.ProductDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoppingCartItemDto {

    private Long id;
    private ProductDto product;
    private Integer quantity;

}
