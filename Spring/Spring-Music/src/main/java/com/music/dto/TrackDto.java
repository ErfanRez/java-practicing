package com.music.dto;

import com.music.model.Cover;
import com.music.model.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.music.constants.Genres;

@Data
public class TrackDto {
    @NotBlank(message = "Track title is required")
    private String title;

    @NotNull(message = "Genre is required")
    private Genres genre;

    public static Song toSongMapper(TrackDto trackDto, String audioKey, String audioUrl, Cover cover) {
        Song song = new Song();
        song.setTitle(trackDto.getTitle());
        song.setGenre(trackDto.getGenre());
        song.setFileKey(audioKey);
        song.setFileUrl(audioUrl);
        song.setCover(cover);
        return song;
    }
}