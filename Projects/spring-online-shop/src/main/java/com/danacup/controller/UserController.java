package com.danacup.controller;

import com.danacup.annotation.Auth;
import com.danacup.annotation.CurrentUser;
import com.danacup.dto.ApiResponse;
import com.danacup.dto.user.UserDto;
import com.danacup.entity.UserEntity;
import com.danacup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Auth
    public ApiResponse<UserDto> getCurrentUser(@CurrentUser UserEntity user) throws ResponseStatusException {
        var userDto = userService.getUserById(user.getId());
        return new ApiResponse<>(true, "successfully", userDto);
    }

}
