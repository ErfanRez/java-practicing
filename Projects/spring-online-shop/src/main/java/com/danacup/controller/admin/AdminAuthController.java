package com.danacup.controller.admin;

import com.danacup.dto.ApiResponse;
import com.danacup.dto.auth.*;
import com.danacup.service.AuthService;
import com.danacup.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthTokenResponseDto> adminLogin(@RequestBody @Valid LoginRequestDto requestDto) throws ResponseStatusException {
        var token = authService.login(requestDto, true);
        return new ApiResponse<>(true, "successfully", token);
    }

}
