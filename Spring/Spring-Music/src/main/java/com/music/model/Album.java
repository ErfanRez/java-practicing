package com.music.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Album {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String name;

    private LocalDate releaseDate;

    private Double price;

    private String coverUrl;
}