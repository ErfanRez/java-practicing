package com.music.config;

import com.music.model.User;
import com.music.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            Optional<User> userOptional = userRepo.findByUsername(username);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new UsernameNotFoundException("User '" + username + "' not found");
            }
        };
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "ARTIST")
                        .requestMatchers("/fav/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/") // Redirect to home page if login is required
                        .loginProcessingUrl("/auth/login") // Custom login endpoint
                        .defaultSuccessUrl("/?login=true") // Redirect after successful login
                        .failureUrl("/?error=true") // Redirect after failed login
                        .permitAll()
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/auth/logout") // Custom logout endpoint
                        .logoutSuccessUrl("/?logout=true") // Redirect after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
