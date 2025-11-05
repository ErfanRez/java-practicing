package com.danacup.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequestDto {

    @NotBlank
    @Length(min = 4, max = 50)
    public String username;
    @NotBlank
    @Length(min = 5, max = 60)
    public String password;

}
