package com.music.repository;

import com.music.model.Album;
import com.music.model.Song;
import com.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select s from Song s order by s.likeCount asc limit 10")
    List<Song> findTopTen();

    @Query("from Song s where s.artist = ?1 and s.album is null ")
    List<Song> findSingleTracksByArtist(User artist);

    List<Song> findByAlbumOrderByCreatedAt(Album album);
}
