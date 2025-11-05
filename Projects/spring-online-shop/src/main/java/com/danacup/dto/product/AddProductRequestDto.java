package com.danacup.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductRequestDto {

    @NotEmpty
    public String title;
    @NotEmpty
    public String description;
    @Min(0)
    public Double price;
    @Min(0)
    public Integer usableBalance;
    @Min(0)
    public Integer lockedBalance;
    @NotNull
    public Long categoryId;

}
