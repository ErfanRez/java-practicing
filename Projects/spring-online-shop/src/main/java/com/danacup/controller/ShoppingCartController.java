package com.danacup.controller;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.cart.AddShoppingCartItemRequestDto;
import com.danacup.dto.cart.ShoppingCartDto;
import com.danacup.dto.cart.ShoppingCartItemDto;
import com.danacup.dto.cart.UpdateShoppingCartItemRequestDto;
import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.SubmitOrderRequestDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.OrderService;
import com.danacup.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/")
    @Auth
    public ApiResponse<ShoppingCartDto> getUserShoppingCart(@CurrentUser UserEntity user) throws ResponseStatusException {
        var order = shoppingCartService.getShoppingCartByUserId(user.getId());
        return new ApiResponse<>(true, "successfully", order);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Auth
    public ApiResponse<ShoppingCartItemDto> addShoppingCartItem(
            @RequestBody @Valid AddShoppingCartItemRequestDto requestDto,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        var shoppingCartItem = shoppingCartService.addShoppingCartItem(requestDto, user.getId());
        return new ApiResponse<>(true, "successfully", shoppingCartItem);
    }

    @PutMapping("/{itemId}")
    @Auth
    public ApiResponse<ShoppingCartItemDto> updateShoppingCartItem(
            @PathVariable("itemId") Long itemId,
            @RequestBody @Valid UpdateShoppingCartItemRequestDto requestDto,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        var shoppingCartItem = shoppingCartService.updateShoppingCartItem(itemId, requestDto, user.getId());
        return new ApiResponse<>(true, "successfully", shoppingCartItem);
    }

    @DeleteMapping("/{itemId}")
    @Auth
    public ApiResponse<Boolean> deleteShoppingCartItem(
            @PathVariable("itemId") Long itemId,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        shoppingCartService.deleteShoppingCartItem(itemId, user.getId());
        return new ApiResponse<>(true, "successfully", true);
    }

}
