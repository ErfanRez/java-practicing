package com.danacup.dto.product;

import lombok.Data;

@Data
public class FilterProductRequestDto {

    public String title;
    public Double minPrice;
    public Double maxPrice;
    public Long categoryId;

}
