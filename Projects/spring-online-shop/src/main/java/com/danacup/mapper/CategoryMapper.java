package com.danacup.mapper;

import com.danacup.dto.category.CategoryDto;
import com.danacup.entity.CategoryEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryDto toDto(CategoryEntity category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .parent(category.getParent() != null ? toDto(category.getParent()) : null)
                .build();
    }

}
