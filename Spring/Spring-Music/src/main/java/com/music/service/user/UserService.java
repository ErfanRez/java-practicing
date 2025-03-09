package com.music.service.user;

import com.music.dto.RegisterDto;
import com.music.model.User;
import com.music.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(RegisterDto registerDto) {
        User user = RegisterDto.dtoToUser(registerDto, passwordEncoder);
        userRepository.save(user);

    }

    @Transactional
    public void saveArtist(RegisterDto registerDto) {
        User artist = RegisterDto.dtoToArtist(registerDto, passwordEncoder);
        userRepository.save(artist);

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
