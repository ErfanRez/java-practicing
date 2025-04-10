package com.music.service.album;

import com.music.dto.AlbumDto;
import com.music.model.Album;
import com.music.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAlbumService {
    void saveAlbumWithSongsAndCover(AlbumDto albumDto, List<String> songTitles, List<MultipartFile> songFiles, User user) throws IOException;

    List<Album> getAllAlbums();


}