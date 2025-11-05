package com.danacup.dto.auth;

import com.danacup.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDto {

    private String accessKey;
    private UserRole role;

}
