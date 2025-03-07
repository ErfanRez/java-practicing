package com.music.controller;


import com.music.dto.CreateAlbumDto;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.service.S3Service;
import com.music.service.album.AlbumService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.music.constants.Constants;

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
    @Autowired
    private AlbumService albumService;

    @Autowired
    private S3Service s3Service;

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
            HttpSession session
    ) {
        if (cover.isEmpty()) {
            model.addAttribute("coverError", "Please select a file to upload.");
            return "new-album";
        }

        if (bindingResult.hasErrors()) {
            return "new-album";
        }

        Path filePath = null;

        try {
            // Ensure the directory exists
            Path directoryPath = Paths.get(Constants.albumDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Save the image to the upload directory
            String fileName = cover.getOriginalFilename();
            filePath = Paths.get(Constants.albumDir + fileName);
            Files.write(filePath, cover.getBytes());


            // Store the relative URL in the session
            String relativeUrl = "/assets/images/albums/" + fileName;
            createAlbumDto.setCoverUrl(relativeUrl);
            log.info("Image saved at: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            // Log the exception
            log.error("Error saving file: " + e.getMessage(), e);
        }

        session.setAttribute("album", createAlbumDto);
        session.setAttribute("coverPath",  filePath);

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
        CreateAlbumDto createAlbumDto = (CreateAlbumDto) session.getAttribute("album");
        if (createAlbumDto == null) {
            redirectAttributes.addFlashAttribute("error", "No album found. Please create an album first.");
            return "redirect:/dashboard/new-album";
        }

        if (songTitles.size() != songFiles.size()) {
            redirectAttributes.addFlashAttribute("error", "Number of song titles and files do not match.");
            return "redirect:/dashboard/album/add-songs";
        }

        List<Song> songs= new ArrayList<>();
        Cover cover = new Cover();
        try {
            // Upload songs to S3
            for (int i = 0; i < songTitles.size(); i++) {
                String songTitle = songTitles.get(i);
                MultipartFile songFile = songFiles.get(i);

                if (songFile.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "One or more song files are empty.");
                    return "redirect:/dashboard/album/add-songs";
                }

                String key = "songs/" + System.currentTimeMillis() + "_" + songFile.getOriginalFilename();
                String url = s3Service.uploadFile(key, songFile.getBytes());
                Song song = new Song();
                song.setTitle(songTitle);
                song.setFileKey(key);
                song.setFileUrl(url);
                song.setGenre(createAlbumDto.getGenre());

                songs.add(song);

            }

            // Upload the cover to S3
            String localCoverPath = session.getAttribute("coverPath").toString();
            if (localCoverPath != null) {
                Path filePath = Paths.get(localCoverPath);
                byte[] coverBytes = Files.readAllBytes(filePath);

                String coverKey = "covers/" + System.currentTimeMillis() + "_" + filePath.getFileName().toString();
                String coverUrl = s3Service.uploadFile(coverKey, coverBytes);

                cover.setKey(coverKey);
                cover.setUrl(coverUrl);


                // Delete the locally saved cover file
                Files.deleteIfExists(filePath);
            }

            albumService.saveAlbumWithSongsAndCover(createAlbumDto, songs, cover);

            session.removeAttribute("album");
            redirectAttributes.addFlashAttribute("success", "Album and songs saved successfully!");
            return "redirect:/dashboard";

        } catch (IOException e) {
            log.error("Error uploading files: " + e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "An error occurred while uploading the files.");
            return "redirect:/dashboard/album/add-songs";
        }
    }

//    @PostMapping("/song/delete/{songId}")
//    public String deleteSong(
//            @PathVariable Long songId,
//            RedirectAttributes redirectAttributes
//    ) {
//        // Find the song in the database
//        Song song = songRepository.findById(songId)
//                .orElseThrow(() -> new RuntimeException("Song not found"));
//
//        try {
//            // Delete the song file from S3
//            s3Service.deleteFile(song.getS3Key());
//
//            // Delete the song record from the database
//            songRepository.delete(song);
//
//            redirectAttributes.addFlashAttribute("success", "Song deleted successfully!");
//        } catch (Exception e) {
//            log.error("Error deleting song: " + e.getMessage(), e);
//            redirectAttributes.addFlashAttribute("error", "An error occurred while deleting the song.");
//        }
//
//        return "redirect:/dashboard"; // Redirect to the dashboard or any other page
//    }
}
