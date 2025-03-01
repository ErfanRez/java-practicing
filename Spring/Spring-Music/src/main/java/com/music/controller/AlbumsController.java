package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlbumsController {

    @GetMapping("/albums")
    public String displayAlbums() {
        return "albums";
    }
}
