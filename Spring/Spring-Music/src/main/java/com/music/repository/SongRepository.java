package com.music.repository;

import com.music.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select s from Song s order by s.likeCount asc limit 10")
    List<Song> findTopTen();
}
