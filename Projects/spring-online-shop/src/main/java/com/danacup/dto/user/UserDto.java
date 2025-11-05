package com.danacup.dto.user;

import com.danacup.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String email;
    private UserRole role;
}
