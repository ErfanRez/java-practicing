package com.music.controller;

import com.music.dto.CreateAlbumDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final String albumDir = "src/main/resources/static/assets/images/albums/";

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
        model.addAttribute("album", new CreateAlbumDto());
        return "new-album";
    }

    @PostMapping("/new-album")
    public String handleNewAlbumForm(
            @RequestParam("cover") MultipartFile cover,
            @Valid @ModelAttribute("album") CreateAlbumDto createAlbumDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        if (cover.isEmpty()) {
            model.addAttribute("coverError", "Please select a file to upload.");
            return "new-album";
        }

        if (bindingResult.hasErrors()) {
            return "new-album";
        }

        try {
            // Ensure the directory exists
            Path directoryPath = Paths.get(albumDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Save the image to the upload directory
            String fileName = cover.getOriginalFilename();
            Path filePath = Paths.get(albumDir + fileName);
            Files.write(filePath, cover.getBytes());

            //To delete file
//            try {
//                Files.deleteIfExists(directoryPath);
//            } catch (IOException deleteEx) {
//                // Log the deletion failure
//                deleteEx.printStackTrace();
//            }


            // Store the relative URL in the session
            String relativeUrl = "/assets/images/albums/" + fileName;
            createAlbumDto.setCoverUrl(relativeUrl);
            System.out.println("Image saved at: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            // Log the exception
            log.error("Error saving file: " + e.getMessage(), e);
        }

        session.setAttribute("album", createAlbumDto);

        return "redirect:/dashboard/album/add-songs";
    }

    @GetMapping("/album/add-songs")
    public String displayAddSongForm(Model model) {

        return "add-songs";
    }

    @PostMapping("/album/add-songs")
    public String handleAddSongsForm(
            @RequestParam("songTitles") List<String> songTitles,
            @RequestParam("songFiles") List<MultipartFile> songFiles,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        // Retrieve the album from the session
        CreateAlbumDto album = (CreateAlbumDto) session.getAttribute("album");
        if (album == null) {
            redirectAttributes.addFlashAttribute("error", "No album found. Please create an album first.");
            return "redirect:/dashboard/new-album";
        }

        // Validate the number of songs
        if (songTitles.size() != songFiles.size()) {
            redirectAttributes.addFlashAttribute("error", "Number of song titles and files do not match.");
            return "redirect:/dashboard/album/add-songs";
        }

        // Directory to save song files
        String songDir = "src/main/resources/static/assets/songs/";
        Path songDirectoryPath = Paths.get(songDir);

        try {
            // Ensure the directory exists
            if (!Files.exists(songDirectoryPath)) {
                Files.createDirectories(songDirectoryPath);
            }

            // Process each song
            for (int i = 0; i < songTitles.size(); i++) {
                String songTitle = songTitles.get(i);
                MultipartFile songFile = songFiles.get(i);

                if (songFile.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "One or more song files are empty.");
                    return "redirect:/dashboard/album/add-songs";
                }

                // Save the song file
                String fileName = System.currentTimeMillis() + "_" + songFile.getOriginalFilename();
                Path filePath = Paths.get(songDir + fileName);
                Files.write(filePath, songFile.getBytes());

                // Associate the song with the album (you can use a service or repository here)
                // For now, we'll just log the details
                log.info("Song Title: " + songTitle);
                log.info("Song File: " + fileName);
            }

            // Clear the session after saving the album
            session.removeAttribute("album");

            redirectAttributes.addFlashAttribute("success", "Album and songs saved successfully!");
            return "redirect:/dashboard";

        } catch (IOException e) {
            log.error("Error saving song files: " + e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "An error occurred while saving the songs.");
            return "redirect:/dashboard/album/add-songs";
        }
    }
}
