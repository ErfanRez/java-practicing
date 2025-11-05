package com.danacup.controller;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.order.OrderDto;
import com.danacup.dto.order.SubmitOrderRequestDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/submit-order")
    @ResponseStatus(HttpStatus.CREATED)
    @Auth
    public ApiResponse<OrderDto> createOrder(
            @RequestBody @Valid SubmitOrderRequestDto requestDto,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        var order = orderService.submitOrder(requestDto, user.getId());
        return new ApiResponse<>(true, "successfully", order);
    }

    @PostMapping("/filter-orders")
    @Auth
    public ApiResponse<Page<OrderDto>> filterOrders(
            @CurrentUser UserEntity user,
            @PageableDefault Pageable pageable
    ) throws ResponseStatusException {
        var orders = orderService.filterOrders(user.getId(), pageable);
        return new ApiResponse<>(true, "successfully", orders);
    }

    @GetMapping("/order-detail/{id}")
    @Auth
    public ApiResponse<OrderDto> getOrderDetails(
            @PathVariable("id") Long id,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        var order = orderService.getOrderById(id, user.getId());
        return new ApiResponse<>(true, "successfully", order);
    }

    @PostMapping("/handle-order-payment/{id}")
    @Auth
    public ApiResponse<OrderDto> handleOrderPayment(
            @PathVariable("id") Long id,
            @RequestParam("isSuccessfully") Boolean isSuccessfully,
            @CurrentUser UserEntity user
    ) throws ResponseStatusException {
        var order = orderService.handleOrderPayment(id, isSuccessfully, user.getId());
        return new ApiResponse<>(true, "successfully", order);
    }
    
}
