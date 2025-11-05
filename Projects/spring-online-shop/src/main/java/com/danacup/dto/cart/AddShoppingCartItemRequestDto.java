package com.danacup.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddShoppingCartItemRequestDto {

    @NotNull
    public Long productId;
    @NotNull
    @Min(1)
    public Integer quantity;

}