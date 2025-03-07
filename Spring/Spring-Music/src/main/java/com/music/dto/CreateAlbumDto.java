package com.music.dto;

import com.music.constants.Genres;
import com.music.model.Album;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAlbumDto {
    @NotBlank(message = "Album name is required")
    private String title;

    @NotNull(message = "Release date is required")
    @FutureOrPresent(message = "Release date can not be in the past")
    private LocalDate releaseDate;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotNull(message = "Genre is required")
    private Genres genre; // Add genre field

    private String coverUrl;

    public static Album DtoToAlbumMapper(CreateAlbumDto createAlbumDto){
        Album album = new Album();
        album.setTitle(createAlbumDto.getTitle());
        album.setReleaseDate(createAlbumDto.getReleaseDate());
        album.setPrice(createAlbumDto.getPrice());
        album.setGenre(createAlbumDto.getGenre());
        return album;
    }
}
