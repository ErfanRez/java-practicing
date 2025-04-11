package com.music.controller;

import com.music.model.Song;
import com.music.service.song.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongsController {
    private final SongService songService;

    public SongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public String displaySongs(Model model){
        List<Song> songs = songService.findAllSongs();

        if(!songs.isEmpty())
            model.addAttribute("songs", songs);

        return "songs";
    }
}
