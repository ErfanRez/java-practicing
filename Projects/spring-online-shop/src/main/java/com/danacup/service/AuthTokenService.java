package com.danacup.service;

import com.danacup.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class AuthTokenService {
    private final UserService userService;
    private final Key accessKey;

    public AuthTokenService(UserService userService) {
        this.userService = userService;
        this.accessKey = Keys.hmacShaKeyFor("advanced-access-token-key-more-than-256-bits".getBytes());
    }

    /**
     * Make new access token (jwt) by the user
     *
     * @param payload
     * @return access token as string
     */
    public String createToken(UserEntity payload) {
        Date issuedAt = new Date();
        String token;
        Date expirationAt = new Date(issuedAt.getTime() + 21600000);
        if (payload.getPhoneNumber() != null) {
            token = Jwts.builder()
                    .setSubject(payload.getUsername())
                    .addClaims(Map.of(
                            "id", payload.getId(),
                            "phoneNumber", payload.getPhoneNumber()
                    ))
                    .setIssuedAt(issuedAt)
                    .setExpiration(expirationAt)
                    .signWith(accessKey)
                    .compact();
        } else {
            token = Jwts.builder()
                    .setSubject(payload.getUsername())
                    .addClaims(Map.of(
                            "id", payload.getId(),
                            "username", payload.getUsername()
                    ))
                    .setIssuedAt(issuedAt)
                    .setExpiration(expirationAt)
                    .signWith(accessKey)
                    .compact();
        }
        return token;
    }

    /**
     * Validate token with extract the data and find the user
     *
     * @param token
     * @return UserEntity founded user with the token details
     * @throws ResponseStatusException
     */
    public UserEntity validateToken(String token) throws ResponseStatusException {
        var parser = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build();

        var claims = parser.parseClaimsJws(token).getBody();
        
        Long id;
        Object idClaim = claims.get("id");
        if (idClaim instanceof Integer) {
            id = ((Integer) idClaim).longValue();
        } else if (idClaim instanceof Long) {
            id = (Long) idClaim;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid ID format in token");
        }

        return userService.findById(id);
    }
}