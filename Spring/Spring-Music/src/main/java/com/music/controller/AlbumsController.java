package com.music.controller;

import com.music.model.Album;
import com.music.model.Song;
import com.music.service.album.AlbumService;
import com.music.service.song.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumsController {
    private final AlbumService albumService;
    private final SongService songService;

    public AlbumsController(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping
    public String displayAlbums(Model model) {
        List<Album> albums = albumService.findAllAlbums();

        if (!albums.isEmpty())
            model.addAttribute("albums", albums);


        return "albums";
    }

    @GetMapping("/{id}")
    public String displaySingleAlbum(@PathVariable Long id, Model model){
        Album album = albumService.findById(id);
        model.addAttribute("album", album);

        List<Song> songs = songService.findByAlbum(album);
        if (!songs.isEmpty())
            model.addAttribute("songs", songs);

        return "album-details";
    }
}
