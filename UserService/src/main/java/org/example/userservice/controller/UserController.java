package org.example.userservice.controller;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.service.AuthService;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest){
        UserResponse savedUser = userService.register(createUserRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }


}
