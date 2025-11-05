package com.danacup.controller.admin;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.order.OrderDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.OrderService;
import com.danacup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/")
    @Auth
    public ApiResponse<List<OrderDto>> getAllOrders(@CurrentUser UserEntity user) throws ResponseStatusException {
        userService.assertUserIsAdmin(user);
        var orders = orderService.getAllOrders();
        return new ApiResponse<>(true, "successfully", orders);
    }

    @PutMapping("/complete-order/{id}")
    @Auth
    public ApiResponse<OrderDto> completeOrder(
            @PathVariable("id") Long id,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var order = orderService.completeOrder(id, requestedUser);
        return new ApiResponse<>(true, "successfully", order);
    }

    @PutMapping("/cancel-order/{id}")
    @Auth
    public ApiResponse<OrderDto> cancelOrder(
            @PathVariable("id") Long id,
            @CurrentUser UserEntity requestedUser
    ) throws ResponseStatusException {
        var order = orderService.cancelOrder(id, requestedUser);
        return new ApiResponse<>(true, "successfully", order);
    }

}
