package com.music.service.song;

import com.music.utils.Constants;
import com.music.dto.TrackDto;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.CoverRepository;
import com.music.repository.SongRepository;
import com.music.service.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SongService implements ISongService {
    private final SongRepository songRepository;
    private final CoverRepository coverRepository;
    private final S3Service s3Service;

    public SongService(SongRepository songRepository, CoverRepository coverRepository, S3Service s3Service) {
        this.songRepository = songRepository;
        this.coverRepository = coverRepository;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public void saveTrackWithCover(TrackDto trackDto, MultipartFile audioFile, MultipartFile coverFile) throws IOException {
        String audioKey = Constants.TRACKS_PREFIX + System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
        String audioUrl = s3Service.uploadFile(audioKey, audioFile.getBytes());

        String coverKey = Constants.COVERS_PREFIX + System.currentTimeMillis() + "_" + coverFile.getOriginalFilename();
        String coverUrl = s3Service.uploadFile(coverKey, coverFile.getBytes());

        Cover cover = new Cover();
        cover.setKey(coverKey);
        cover.setUrl(coverUrl);
        Cover savedCover = coverRepository.save(cover);

        Song song = TrackDto.toSongMapper(trackDto, audioKey, audioUrl, savedCover);
        songRepository.save(song);
    }
}