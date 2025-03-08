package com.music.service.album;

import com.music.dto.AlbumDto;
import com.music.model.Cover;
import com.music.model.Song;

import java.io.IOException;
import java.util.List;

public interface IAlbumService {
    void saveAlbumWithSongsAndCover(AlbumDto albumDto, List<Song> songs, Cover cover);
}
