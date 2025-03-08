package com.music.controller;


import com.music.dto.AlbumDto;
import com.music.dto.TrackDto;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.service.S3Service;
import com.music.service.album.AlbumService;
import com.music.service.song.SongService;
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
    private final AlbumService albumService;
    private final S3Service s3Service;
    private final SongService songService;

    public DashboardController(AlbumService albumService, S3Service s3Service, SongService songService) {
        this.albumService = albumService;
        this.s3Service = s3Service;
        this.songService = songService;
    }

    @GetMapping()
    public String displayDashboard(){
        return "dashboard";
    }

    @GetMapping("/new-track")
    public String showNewTrackPage(Model model) {
        model.addAttribute("track", new TrackDto());
        return "new-track";
    }

    @PostMapping("/new-track")
    public String handleNewTrackForm(
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("cover") MultipartFile cover,
            @Valid @ModelAttribute("track") TrackDto trackDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (audioFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select an audio file to upload.");
            return "redirect:/dashboard/new-track";
        }

        if (cover.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a cover picture to upload.");
            return "redirect:/dashboard/new-track";
        }

        if (bindingResult.hasErrors()) {
            return "new-track";
        }

        try {
            // Upload the audio file to S3
            String audioKey = "tracks/" + System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
            String audioUrl = s3Service.uploadFile(audioKey, audioFile.getBytes());

            // Upload the cover picture to S3
            String coverKey = "covers/" + System.currentTimeMillis() + "_" + cover.getOriginalFilename();
            String coverUrl = s3Service.uploadFile(coverKey, cover.getBytes());

            // Save the track and cover to the database
            songService.saveTrack(trackDto, audioKey, audioUrl, coverKey, coverUrl);

            redirectAttributes.addFlashAttribute("success", "Track saved successfully!");
            return "redirect:/dashboard";

        } catch (IOException e) {
            log.error("Error uploading files: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("saveError", "An error occurred while uploading the track.");
            return "redirect:/dashboard/new-track";
        }
    }

    @GetMapping("/new-album")
    public String displayNewAlbum(Model model){
        model.addAttribute("album", new AlbumDto());
        return "new-album";
    }

    @PostMapping("/new-album")
    public String handleNewAlbumForm(
            @RequestParam("cover") MultipartFile cover,
            @Valid @ModelAttribute("album") AlbumDto createAlbumDto,
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
            String relativeUrl = "/assets/images/covers/" + fileName;
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
    public synchronized String handleAddSongsForm(
            @RequestParam("songTitles") List<String> songTitles,
            @RequestParam("songFiles") List<MultipartFile> songFiles,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {

        AlbumDto createAlbumDto = (AlbumDto) session.getAttribute("album");
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
            return "redirect:/dashboard/new-album";
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
