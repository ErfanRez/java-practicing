package com.danacup.service;

import com.danacup.dto.auth.RegisterRequestDto;
import com.danacup.dto.user.UserDto;
import com.danacup.entity.UserEntity;
import com.danacup.entity.UserEntity_;
import com.danacup.enums.UserRole;
import com.danacup.exception.ApiError;
import com.danacup.mapper.UserMapper;
import com.danacup.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Make new user for the registration process
     *
     * @param requestDto
     * @return UserEntity
     */
    @Transactional
    public UserEntity createUser(RegisterRequestDto requestDto) {
        var user = this.saveUser(
                UserEntity.builder()
                        .username(requestDto.username)
                        .email(requestDto.email)
                        .password(bCryptPasswordEncoder.encode(requestDto.password))
                        .role(UserRole.USER)
                        .build()
        );
        logger.info("New user created with id: {}", user.getId());
        return user;
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userEntityRepository.findOne((r, q, b) -> b.equal(r.get(UserEntity_.USERNAME), username))
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long id) throws ResponseStatusException {
        return userEntityRepository.findById(id).orElseThrow(() -> new ApiError.NotFound("user-not-found"));
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) throws ResponseStatusException {
        var user = this.findById(id);
        return userMapper.toDto(user);
    }

    public UserEntity findByPhoneNumber(String phoneNumber) {
        return userEntityRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    public UserEntity createUserWithPhoneNumber(String phoneNumber) {
        UserEntity user = new UserEntity();
        user.setPhoneNumber(phoneNumber);
        return userEntityRepository.save(user);
    }

    @Transactional
    public UserEntity saveUser(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }

    /**
     * Check the expected user admin role
     *
     * @param user
     * @throws ResponseStatusException
     */
    public void assertUserIsAdmin(UserEntity user) throws ResponseStatusException {
        if (user.getRole() != UserRole.ADMIN) {
            throw new ApiError.Forbidden("invalid-user-admin-access");
        }
    }

}
