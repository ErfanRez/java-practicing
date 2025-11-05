package com.danacup.controller;

import com.danacup.dto.ApiResponse;
import com.danacup.dto.product.FilterProductRequestDto;
import com.danacup.dto.product.ProductDto;
import com.danacup.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ApiResponse<Page<ProductDto>> filterProducts(FilterProductRequestDto filterDto, @PageableDefault Pageable pageable) throws ResponseStatusException {
        var products = productService.filterProducts(filterDto, pageable);
        return new ApiResponse<>(true, "successfully", products);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);

        return new ApiResponse<>(true, "OK", product);
    }

}
