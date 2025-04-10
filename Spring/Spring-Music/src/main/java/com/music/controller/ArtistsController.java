package com.music.controller;

import com.music.model.User;
import com.music.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArtistsController {
    private final UserService userService;

    public ArtistsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/artists")
    public String displayArtists(Model model){
        List<User> artists = userService.findAllArtists();

        if(!artists.isEmpty())
            model.addAttribute("artists", artists);

        return "artists";
    }
}
