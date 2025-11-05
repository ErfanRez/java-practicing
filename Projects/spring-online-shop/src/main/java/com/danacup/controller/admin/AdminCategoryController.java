package com.danacup.controller.admin;

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
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    @Auth
    public ApiResponse<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return new ApiResponse<>(true, "OK", allCategories);
    }

    @GetMapping("/{id}")
    @Auth
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);

        return new ApiResponse<>(true, "OK", category);
    }

    @PostMapping("/")
    @Auth
    public ApiResponse<CategoryDto> createCategory(
            @RequestBody @Valid AddCategoryRequestDto requestDto,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var category = categoryService.createCategory(requestDto, requestedUser);
        return new ApiResponse<>(true, "successfully", category);
    }

    @PutMapping("/{id}")
    @Auth
    public ApiResponse<CategoryDto> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateCategoryRequestDto requestDto,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var category = categoryService.updateCategory(id, requestDto, requestedUser);
        return new ApiResponse<>(true, "successfully", category);
    }

    @DeleteMapping("/{id}")
    @Auth
    public ApiResponse<Boolean> deleteCategory(
            @PathVariable("id") Long id,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        categoryService.deleteCategory(id, requestedUser);
        return new ApiResponse<>(true, "successfully", true);
    }

}
