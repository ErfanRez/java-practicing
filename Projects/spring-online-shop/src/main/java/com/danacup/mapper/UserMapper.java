package com.danacup.mapper;

import com.danacup.dto.user.UserDto;
import com.danacup.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }


}
