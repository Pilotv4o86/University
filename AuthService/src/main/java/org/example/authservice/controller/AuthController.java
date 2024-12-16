package org.example.authservice.controller;

import lombok.AllArgsConstructor;
import org.example.authservice.dto.UserResponse;
import org.example.authservice.model.User;
import org.example.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping
    public UserResponse createUser(@RequestBody User user) {
        return authService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody User user) {
        return authService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        authService.deleteUser(id);
    }
}


