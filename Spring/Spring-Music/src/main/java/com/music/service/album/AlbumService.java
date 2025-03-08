package com.music.service.album;

import com.music.dto.AlbumDto;
import com.music.model.Album;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.CoverRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class AlbumService implements IAlbumService {

    private final AlbumRepository albumRepository;

    private final CoverRepository coverRepository;

    public AlbumService(AlbumRepository albumRepository, CoverRepository coverRepository) {
        this.albumRepository = albumRepository;
        this.coverRepository = coverRepository;
    }

    @Override
    @Transactional
    public void saveAlbumWithSongsAndCover(AlbumDto albumDto, List<Song> songs, Cover cover) {
        if (albumDto == null || songs == null || cover == null) {
            throw new IllegalArgumentException("AlbumDto, songs, and cover must not be null");
        }

        Album album = AlbumDto.DtoToAlbumMapper(albumDto);

        Cover savedCover = coverRepository.save(cover);
        album.setCover(savedCover);


        for (Song song : songs) {
            album.addSong(song);
            song.setCover(savedCover);
        }

        Album savedAlbum = albumRepository.save(album);

        log.info("Album, cover, and songs saved successfully. Album ID: {}", savedAlbum.getId());
    }

//    public Album createAlbum(String title, LocalDate releaseDate, Double price, Genres genre, Long artistId, Cover cover) {
//        Album album = new Album();
//        album.setTitle(title);
//        album.setReleaseDate(releaseDate);
//        album.setPrice(price);
//        album.setGenre(genre);
//        album.setCover(cover);
//
//        Artist artist = artistRepository.findById(artistId)
//                .orElseThrow(() -> new RuntimeException("Artist not found"));
//        album.setArtist(artist);
//
//        return albumRepository.save(album);
//    }
//
//    public List<Album> getAllAlbums() {
//        return albumRepository.findAll();
//    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
    }
}
