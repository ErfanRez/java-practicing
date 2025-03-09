package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fav")
public class FavoritesController {

    @GetMapping("/songs")
    public String displayFavSongs() {
        return "fav-songs";
    }


    @GetMapping("/albums")
    public String displayFavAlbums() {
        return "fav-albums";
    }
}
