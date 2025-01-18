package org.example.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.error.ErrorMessages;
import org.example.userservice.exception.AccessDeniedException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.properties.JwtProperties;
import org.example.userservice.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserMapper userMapper;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String username, Set<Role> roles) {
        ClaimsBuilder claimsBuilder= Jwts.claims().subject(username);
        claimsBuilder.add("id", userId);
        claimsBuilder.add("roles", resolveRoles(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        claimsBuilder.expiration(validity);
        claimsBuilder.issuedAt(now);
        Claims claims = claimsBuilder.build();
        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(Long userId, String username) {
        ClaimsBuilder claimsBuilder = Jwts.claims().subject(username);
        claimsBuilder.add("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        claimsBuilder.expiration(validity);
        claimsBuilder.issuedAt(now);
        return Jwts.builder()
                .claims(claimsBuilder.build())
                .signWith(key)
                .compact();

    }

    @Transactional
    public UserResponse refreshUserTokens(String refreshToken) {

        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException(String.format(ErrorMessages.ACCESS_DENIED));
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userMapper.toUserResponse(userService.getUserById(userId));
        return new UserResponse(
                userId, user.getUsername(),
                createAccessToken(userId, user.getUsername(), user.getRoles()),
                createRefreshToken(userId, user.getUsername()));
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims= Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return !claims.getPayload().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                //.parseClaimsJws(token)
                .parseSignedClaims(token)
                .getPayload()
                .getId();
    }

    private String getUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }
}
