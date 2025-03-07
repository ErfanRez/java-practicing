package com.music.model;

import com.music.constants.Genres;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Album extends BaseEntity {

    private String title;

    private LocalDate releaseDate;

    private Double price;

    @Enumerated(EnumType.STRING)
    private Genres genre;

    @OneToOne(fetch = FetchType.EAGER)
    private Cover cover;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();
}