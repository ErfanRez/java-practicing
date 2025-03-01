package com.music.controller;

import com.music.model.Album;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping()
    public String displayDashboard(){
        return "dashboard";
    }

    @GetMapping("/add-songs")
    public String showAddSongsPage(@PathVariable(required = false) Long albumId) {
        return "add-songs"; // Renders the add-songs.html page
    }

    @GetMapping("/new-album")
    public String displayNewAlbum(Model model){
        model.addAttribute("album", new Album());
        return "new-album";
    }

    @PostMapping("/new-album")
    public String handleNewAlbumForm(
            @Valid @ModelAttribute("album") Album album,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "new-album";
        }

        // Save the album (replace with actual logic)
        Long albumId = saveAlbum(album);

        // Add album details to flash attributes
        redirectAttributes.addFlashAttribute("albumName", album.getName());
        redirectAttributes.addFlashAttribute("albumCover", album.getCover());

        // Redirect to the add-song page for the new album
        redirectAttributes.addAttribute("albumId", albumId);
        return "redirect:/dashboard/album/{albumId}/add-songs";
    }

    @GetMapping("/album/{albumId}/add-songs")
    public String displayAddSongForm(@PathVariable Long albumId, Model model) {
        // Fetch the album by ID (replace with actual logic)
        Album album = getAlbumById(albumId);
        model.addAttribute("album", album);
        model.addAttribute("albumId", albumId);
        return "add-songs";
    }

    // Dummy methods for now (replace with actual service calls)
    private Long saveAlbum(Album album) {
        // Save the album to the database and return its ID
        return 1L; // Replace with actual logic
    }

    private Album getAlbumById(Long albumId) {
        // Fetch the album from the database
        return new Album(); // Replace with actual logic
    }
}
