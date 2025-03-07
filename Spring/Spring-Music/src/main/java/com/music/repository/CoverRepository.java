package com.music.repository;

import com.music.model.Cover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoverRepository extends JpaRepository<Cover, Long> {
}
