package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongsController {

    @GetMapping("/songs")
    public String displaySongs(){
        return "songs";
    }
}
