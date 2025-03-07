package com.music.service.artist;

import com.music.model.Artist;
import com.music.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public Artist createArtist(String name, String nickname) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setNickname(nickname);
        return artistRepository.save(artist);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));
    }
}
