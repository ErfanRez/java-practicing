package com.music.service.song;

import com.music.dto.TrackDto;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.CoverRepository;
import com.music.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService implements ISongService {

    private final SongRepository songRepository;
    private final CoverRepository coverRepository;

    public SongService(SongRepository songRepository, CoverRepository coverRepository) {
        this.songRepository = songRepository;
        this.coverRepository = coverRepository;
    }

    @Transactional
    @Override
    public void saveTrack(TrackDto trackDto, String audioKey, String audioUrl, String coverKey, String coverUrl) {
        Cover cover = new Cover();
        cover.setKey(coverKey);
        cover.setUrl(coverUrl);
        Cover savedCover = coverRepository.save(cover);

        Song song = TrackDto.toSongMapper(trackDto, audioKey, audioUrl, savedCover);

        songRepository.save(song);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
