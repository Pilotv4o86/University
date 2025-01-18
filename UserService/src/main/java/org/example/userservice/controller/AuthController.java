package org.example.userservice.controller;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody CreateUserRequest userRequest){
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
