package com.music.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class Album {

    @NotBlank(message = "Album name is required")
    private String name;

    @NotNull(message = "Release date is required")
    @FutureOrPresent(message = "Release date can not be in the past")
    private LocalDate releaseDate;

    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Album cover is required")
    private MultipartFile cover;

    private String coverUrl;
}