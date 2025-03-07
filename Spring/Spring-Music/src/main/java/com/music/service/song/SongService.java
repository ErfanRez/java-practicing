package com.music.service.song;

import com.music.constants.Genres;
import com.music.model.Album;
import com.music.model.Artist;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.ArtistRepository;
import com.music.repository.CoverRepository;
import com.music.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CoverRepository coverRepository;

    public Song createSong(String title, String fileUrl) {
        Song song = new Song();
        song.setTitle(title);
        song.setFileUrl(fileUrl);
//        song.setGenre(genre);
//        song.setCover(cover);

//        Album album = albumRepository.findById(albumId)
//                .orElseThrow(() -> new RuntimeException("Album not found"));
//        song.setAlbum(album);
//
//        Artist artist = artistRepository.findById(artistId)
//                .orElseThrow(() -> new RuntimeException("Artist not found"));
//        song.setArtist(artist);

        return songRepository.save(song);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
