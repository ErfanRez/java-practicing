package com.music.service.album;

import com.music.model.User;
import com.music.repository.UserRepository;
import com.music.utils.Constants;
import com.music.dto.AlbumDto;
import com.music.model.Album;
import com.music.model.Cover;
import com.music.model.Song;
import com.music.repository.AlbumRepository;
import com.music.repository.CoverRepository;
import com.music.repository.SongRepository;
import com.music.service.S3Service;
import com.music.utils.Roles;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AlbumService implements IAlbumService {
    private final AlbumRepository albumRepository;
    private final CoverRepository coverRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final HttpSession session;

    public AlbumService(AlbumRepository albumRepository, CoverRepository coverRepository, UserRepository userRepository, S3Service s3Service, HttpSession session) {
        this.albumRepository = albumRepository;
        this.coverRepository = coverRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
        this.session = session;
    }

    @Override
    @Transactional
    public void saveAlbumWithSongsAndCover(AlbumDto albumDto, List<String> songTitles, List<MultipartFile> songFiles, User auth) throws IOException {
        User user = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username:" + auth.getUsername() + " not found"));

        String localCoverPath = session.getAttribute("coverPath").toString();

        Cover cover = new Cover();
        if (localCoverPath != null) {
            Path filePath = Paths.get(localCoverPath);
            byte[] coverBytes = Files.readAllBytes(filePath);

            String coverKey = Constants.COVERS_PREFIX + System.currentTimeMillis() + "_" + filePath.getFileName().toString();
            String coverUrl = s3Service.uploadFile(coverKey, coverBytes);

            cover.setKey(coverKey);
            cover.setUrl(coverUrl);


            // Delete the locally saved cover file
            Files.deleteIfExists(filePath);
        }

        Cover savedCover = coverRepository.save(cover);

        Album album = AlbumDto.DtoToAlbumMapper(albumDto);
        album.setCover(savedCover);
        album.setArtist(user);

        for (int i = 0; i < songTitles.size(); i++) {
            String songTitle = songTitles.get(i);
            MultipartFile songFile = songFiles.get(i);

            String songKey = Constants.SONGS_PREFIX + System.currentTimeMillis() + "_" + songFile.getOriginalFilename();
            String songUrl = s3Service.uploadFile(songKey, songFile.getBytes());

            Song song = new Song();
            song.setTitle(songTitle);
            song.setFileKey(songKey);
            song.setFileUrl(songUrl);
            song.setGenre(album.getGenre());
            song.setCover(savedCover);
            song.setArtist(user);

            album.addSong(song);
        }

        albumRepository.save(album);
    }
}