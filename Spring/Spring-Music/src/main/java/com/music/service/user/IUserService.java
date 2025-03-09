package com.music.service.user;

import com.music.dto.RegisterDto;

public interface IUserService {

    void saveUser(RegisterDto registerDto);

    void saveArtist(RegisterDto registerDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
