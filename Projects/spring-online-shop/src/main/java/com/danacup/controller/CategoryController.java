package com.danacup.controller;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.category.AddCategoryRequestDto;
import com.danacup.dto.category.CategoryDto;
import com.danacup.dto.category.UpdateCategoryRequestDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ApiResponse<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return new ApiResponse<>(true, "OK", allCategories);
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);

        return new ApiResponse<>(true, "OK", category);
    }

}
