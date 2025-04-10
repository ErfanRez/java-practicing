package com.music.repository;

import com.music.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

     @Query("from Album a order by a.likeCount asc limit 10")
     List<Album> findTopTen();
}
