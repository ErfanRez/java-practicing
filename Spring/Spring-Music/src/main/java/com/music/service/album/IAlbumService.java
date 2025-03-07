package com.music.service.album;

import com.music.dto.CreateAlbumDto;
import com.music.model.Cover;
import com.music.model.Song;

import java.util.List;

public interface IAlbumService {
    void saveAlbumWithSongsAndCover(CreateAlbumDto albumDto, List<Song> songs, Cover cover);
}
