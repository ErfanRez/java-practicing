package com.music.service.song;

import com.music.model.User;
import com.music.repository.UserRepository;
import com.music.utils.Constants;
import com.music.dto.TrackDto;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.SongRepository;
import com.music.service.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SongService implements ISongService {
    private final SongRepository songRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;

    public SongService(SongRepository songRepository, S3Service s3Service, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.s3Service = s3Service;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveTrackWithCover(TrackDto trackDto, MultipartFile audioFile, MultipartFile coverFile, User auth) throws IOException {
        User user = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username:" + auth.getUsername() + " not found"));

        String audioKey = Constants.TRACKS_PREFIX + System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
        String audioUrl = s3Service.uploadFile(audioKey, audioFile.getBytes());

        String coverKey = Constants.COVERS_PREFIX + System.currentTimeMillis() + "_" + coverFile.getOriginalFilename();
        String coverUrl = s3Service.uploadFile(coverKey, coverFile.getBytes());

        Cover cover = new Cover();
        cover.setKey(coverKey);
        cover.setUrl(coverUrl);

        Song song = TrackDto.toSongMapper(trackDto, audioKey, audioUrl, cover);
        song.setArtist(user);
        songRepository.save(song);
    }
}