package com.music.repository;

import com.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmailOrNickname(String username, String email, String nickname);
}
