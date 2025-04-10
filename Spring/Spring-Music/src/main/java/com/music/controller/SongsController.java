package com.music.controller;

import com.music.model.Song;
import com.music.service.song.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SongsController {
    private final SongService songService;

    public SongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    public String displaySongs(Model model){
        List<Song> songs = songService.findAllSongs();

        if(!songs.isEmpty())
            model.addAttribute("songs", songs);

        return "songs";
    }
}
