package com.music.service.user;

import com.music.dto.ArtistDto;
import com.music.dto.RegisterDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {

    void saveUser(RegisterDto registerDto);

    void saveArtist(ArtistDto artistDto, MultipartFile profilePicture) throws IOException;

    boolean existsByUsernameOrEmailOrNickname(String username, String email, String nickname);

}
