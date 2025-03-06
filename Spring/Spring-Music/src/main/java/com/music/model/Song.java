package com.music.model;

import lombok.Data;

//@Entity
@Data
public class Song {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileUrl;

//    @ManyToOne
//    @JoinColumn(name = "album_id")
    private Album album;
}
