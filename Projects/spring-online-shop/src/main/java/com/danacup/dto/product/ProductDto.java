package com.danacup.dto.product;

import com.danacup.dto.category.CategoryDto;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer usableBalance;
    private Integer lockedBalance;
    private CategoryDto category;

}
