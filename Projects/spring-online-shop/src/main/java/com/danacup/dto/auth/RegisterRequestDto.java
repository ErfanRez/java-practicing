package com.danacup.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequestDto {

    @NotBlank
    @Length(min = 4, max = 50)
    public String username;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    @Length(min = 5, max = 60)
    public String password;

}
