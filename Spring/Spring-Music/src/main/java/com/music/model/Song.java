package com.music.model;

import com.music.constants.Genres;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Song extends BaseEntity {

    private String title;

    @Column(unique = true)
    private String fileUrl;

    @Column(unique = true)
    private String fileKey;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    @ManyToOne
    @JoinColumn(name = "cover_id")
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;
}

