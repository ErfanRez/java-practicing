package com.danacup.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberDto {
    @NotBlank
    private String phoneNumber;
}
