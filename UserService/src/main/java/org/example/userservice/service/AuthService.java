package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.security.JwtTokenProvider;
import org.example.userservice.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserResponse login(CreateUserRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        User user = userMapper.fromCreatetoUser(userService.getUserByUsername(loginRequest.getUsername()));
        return new UserResponse(user.getId(), user.getUsername(),
                jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()),
                jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
    }


}
