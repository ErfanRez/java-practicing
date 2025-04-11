package com.music.controller;

import com.music.model.Album;
import com.music.service.album.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AlbumsController {
    private final AlbumService albumService;

    public AlbumsController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public String displayAlbums(Model model) {
        List<Album> albums = albumService.findAllAlbums();

        if (!albums.isEmpty())
            model.addAttribute("albums", albums);


        return "albums";
    }
}
