package com.music.service;

import com.music.model.Cover;
import com.music.repository.CoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoverService {

    @Autowired
    private CoverRepository coverRepository;

    public Cover createCover(String key, String url) {
        Cover cover = new Cover();
        cover.setKey(key);
        cover.setUrl(url);
        return coverRepository.save(cover);
    }

    public Cover getCoverById(Long id) {
        return coverRepository.findById(id).orElseThrow(() -> new RuntimeException("Cover not found"));
    }
}
