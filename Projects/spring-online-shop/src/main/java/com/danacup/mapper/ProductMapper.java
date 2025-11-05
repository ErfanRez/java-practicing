package com.danacup.mapper;

import com.danacup.dto.product.ProductDto;
import com.danacup.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductDto toDto(ProductEntity product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .usableBalance(product.getUsableBalance())
                .lockedBalance(product.getLockedBalance())
                .category(categoryMapper.toDto(product.getCategory()))
                .build();
    }


}
