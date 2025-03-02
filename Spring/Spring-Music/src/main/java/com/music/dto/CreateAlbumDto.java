package com.music.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAlbumDto {
    @NotBlank(message = "Album name is required")
    private String name;

    @NotNull(message = "Release date is required")
    @FutureOrPresent(message = "Release date can not be in the past")
    private LocalDate releaseDate;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    private String coverUrl;
}
