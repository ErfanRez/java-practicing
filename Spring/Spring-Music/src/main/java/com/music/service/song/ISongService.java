package com.music.service.song;


import com.music.dto.TrackDto;
import com.music.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ISongService {
    void saveTrackWithCover(TrackDto trackDto, MultipartFile audioFile, MultipartFile coverFile, User user) throws IOException;
}