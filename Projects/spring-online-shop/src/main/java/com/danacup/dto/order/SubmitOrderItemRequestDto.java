package com.danacup.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitOrderItemRequestDto {

    @NotNull
    public Long productId;
    @NotNull
    @Min(0)
    private Integer productVersion;
    @NotNull
    @Min(1)
    public Integer quantity;

}
