package org.example.userservice.controller;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.user.LoginUserRequest;
import org.example.userservice.dto.user.UserResponse;
import org.example.userservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    @PermitAll
    @PostMapping("/university/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginUserRequest userRequest){
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
