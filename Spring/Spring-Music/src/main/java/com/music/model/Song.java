package com.music.model;

import com.music.utils.Genres;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Song extends BaseEntity {

    private String title;

    @Column(unique = true)
    private String audioUrl;

    @Column(unique = true)
    private String audioKey;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    private long likeCount = 0;

    @Embedded
    @Column(nullable = false)
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;
}

