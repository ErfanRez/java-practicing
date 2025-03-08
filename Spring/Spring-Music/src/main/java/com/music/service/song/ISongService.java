package com.music.service.song;


import com.music.dto.TrackDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ISongService {
    void saveTrack(TrackDto trackDto, String audioKey, String audioUrl, String coverKey, String coverUrl);
}
