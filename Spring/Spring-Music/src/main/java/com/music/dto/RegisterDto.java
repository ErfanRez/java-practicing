package com.music.dto;

import com.music.model.User;
import com.music.utils.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterDto {
    private String firstName;

    private String lastName;

    private String nickname;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private Roles role; // Role: USER

    @NotBlank(message = "Password is required")
    private String password;

    public RegisterDto() {
    }

    public static User dtoToArtist(RegisterDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setNickname(dto.getNickname());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }

    public static User dtoToUser(RegisterDto dto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }
}
