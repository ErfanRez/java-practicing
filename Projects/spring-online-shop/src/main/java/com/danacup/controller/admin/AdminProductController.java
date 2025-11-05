package com.danacup.controller.admin;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.product.AddProductRequestDto;
import com.danacup.dto.product.FilterProductRequestDto;
import com.danacup.dto.product.ProductDto;
import com.danacup.dto.product.UpdateProductRequestDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @GetMapping("/")
    @Auth
    public ApiResponse<List<ProductDto>> getAllProducts() {
        List<ProductDto> allProducts = productService.getAllProducts();
        return new ApiResponse<>(true, "OK", allProducts);
    }

    @GetMapping("/{id}")
    @Auth
    public ApiResponse<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);

        return new ApiResponse<>(true, "OK", product);
    }

    @PostMapping("/filter-products")
    @Auth
    public ApiResponse<Page<ProductDto>> filterProducts(FilterProductRequestDto filterDto, @PageableDefault Pageable pageable) throws ResponseStatusException {
        var products = productService.filterProducts(filterDto, pageable);
        return new ApiResponse<>(true, "successfully", products);
    }

    @PostMapping("/")
    @Auth
    public ApiResponse<ProductDto> createProduct(
            @RequestBody @Valid AddProductRequestDto requestDto,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var product = productService.createProduct(requestDto, requestedUser);
        return new ApiResponse<>(true, "successfully", product);
    }

    @PatchMapping("/{id}")
    @Auth
    public ApiResponse<ProductDto> updateProduct(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateProductRequestDto requestDto,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var product = productService.updateProduct(id, requestDto, requestedUser);
        return new ApiResponse<>(true, "successfully", product);
    }

    @DeleteMapping("/{id}")
    @Auth
    public ApiResponse<Boolean> deleteProduct(
            @PathVariable("id") Long id,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        productService.deleteProduct(id, requestedUser);
        return new ApiResponse<>(true, "successfully", true);
    }

}
