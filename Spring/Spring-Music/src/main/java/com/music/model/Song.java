package com.music.model;

import com.music.utils.Genres;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Song extends BaseEntity {

    private String title;

    @Column(unique = true)
    private String fileUrl;

    @Column(unique = true)
    private String fileKey;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    private long likeCount = 0;

    @Embedded
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = true)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;
}

