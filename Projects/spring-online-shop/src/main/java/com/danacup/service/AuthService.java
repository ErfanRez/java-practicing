package com.danacup.service;

import com.danacup.dto.auth.AuthTokenResponseDto;
import com.danacup.dto.auth.LoginRequestDto;
import com.danacup.dto.auth.RegisterRequestDto;
import com.danacup.entity.UserEntity;
import com.danacup.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final OtpService otpService;


    /**
     * Login with the usernamd and password (default credentials)
     * @param requestDto
     * @return AuthTokenResponse the jwt access token
     * @throws ResponseStatusException
     */
    public AuthTokenResponseDto login(LoginRequestDto requestDto, Boolean asAdmin) {
        var user = userService.findByUsername(requestDto.username);

        if (user == null) {
            throw new ApiError.NotFound("User not found");
        }

        if (!bCryptPasswordEncoder.matches(requestDto.password, user.getPassword())) {
            throw new ApiError.Unauthorized("Invalid credentials");
        }

        if (asAdmin) {
            userService.assertUserIsAdmin(user);
        }

        var token = authTokenService.createToken(user);
        return AuthTokenResponseDto.builder()
                .accessKey(token)
                .role(user.getRole())
                .build();
    }

    /**
     * Register user with username and password (aefault credentials)
     * @param requestDto
     * @return boolean the state of the registration (default true)
     * @throws ResponseStatusException
     */
    public boolean register(RegisterRequestDto requestDto) {
        var user = userService.findByUsername(requestDto.username);

        if (user != null) {
            throw new ApiError.Conflict("Username is already in use");
        }

        user = userService.createUser(requestDto);
        return true;
    }

    public UserEntity validateToken(String token){
        var user = authTokenService.validateToken(token);
        logger.info("User with username: {} has been validated", user.getUsername());
        return user;
    }

    public String generateOtp(String phoneNumber) {
        String otp = String.format("%04d", (int)(Math.random() * 9000) + 1000);
        otpService.storeOtp(phoneNumber, otp);
        return otp;
    }

    public AuthTokenResponseDto verifyOtp(String phoneNumber, String otp) {
        if (!otpService.isValidOtp(phoneNumber, otp)) {
            throw new ApiError.Unauthorized("Invalid otp");
        }

        UserEntity user = userService.findByPhoneNumber(phoneNumber);
        if (user == null) {
            user = userService.createUserWithPhoneNumber(phoneNumber);
        }

        String token = authTokenService.createToken(user);
        return AuthTokenResponseDto.builder()
                .accessKey(token)
                .build();
    }

}