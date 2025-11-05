package com.danacup.controller;

import com.danacup.dto.ApiResponse;
import com.danacup.dto.auth.*;
import com.danacup.service.AuthService;
import com.danacup.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/login")
    public ApiResponse<AuthTokenResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto) throws ResponseStatusException {
        var token = authService.login(requestDto, false);
        return new ApiResponse<>(true, "successfully", token);
    }

    @PostMapping("/register")
    public ApiResponse<Boolean> register(@RequestBody @Valid RegisterRequestDto requestDto) throws ResponseStatusException {
        var registered = authService.register(requestDto);
        return new ApiResponse<>(true, "successfully", registered);
    }

}
