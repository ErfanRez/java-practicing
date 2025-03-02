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
}
