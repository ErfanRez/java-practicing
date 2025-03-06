package com.music.service.album;

import com.music.dto.CreateAlbumDto;

import java.util.List;

public interface IAlbumService {
    void saveAlbumWithSongs(CreateAlbumDto albumDto, List<String> songTitles, List<String> songFileUrls);
}
