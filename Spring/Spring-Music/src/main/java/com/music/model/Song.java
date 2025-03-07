package com.music.model;

import com.music.constants.Genres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Song extends BaseEntity {

    private String title;
    private String fileUrl;
    private String fileKey;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @ManyToOne(optional = true)
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
}

