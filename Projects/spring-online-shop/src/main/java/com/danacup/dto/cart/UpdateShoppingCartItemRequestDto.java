package com.danacup.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateShoppingCartItemRequestDto {

    @NotNull
    @Min(1)
    public Integer quantity;

}