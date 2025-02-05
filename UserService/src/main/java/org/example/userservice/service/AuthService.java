package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.user.LoginUserRequest;
import org.example.userservice.dto.user.UserResponse;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponse login(LoginUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new NoSuchElementException("Invalid password");
        }
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().name());
    }

}
