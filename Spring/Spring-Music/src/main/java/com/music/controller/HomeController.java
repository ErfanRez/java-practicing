package com.music.controller;

import com.music.model.Album;
import com.music.model.Song;
import com.music.service.album.AlbumService;
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
    private final AlbumService albumService;

    public HomeController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String displayHome(Model model) {
        List<Song> songs = songService.findTopTen();
        List<Album> albums = albumService.findTopTen();

        if (!songs.isEmpty())
            model.addAttribute("songs", songs);

        if(!albums.isEmpty())
            model.addAttribute("albums", albums);

        return "home";
    }
}
