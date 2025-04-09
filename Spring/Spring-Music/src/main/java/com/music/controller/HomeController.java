package com.music.controller;

import com.music.model.Song;
import com.music.service.song.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class HomeController {
    private final SongService songService;

    public HomeController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public String displayHome(Model model) {
        List<Song> songs = songService.findTopTen();

        if (!songs.isEmpty()) {
            model.addAttribute("songs", songs);
        }

        return "home";
    }
}
