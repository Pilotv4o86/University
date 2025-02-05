package org.example.userservice.controller;

import lombok.AllArgsConstructor;
import org.example.userservice.dto.user.CreateUserRequest;
import org.example.userservice.dto.user.UpdateUserRequest;
import org.example.userservice.dto.user.UserResponse;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest){
        UserResponse savedUser = userService.register(createUserRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @RequestBody UpdateUserRequest updateUserRequest){
        UserResponse savedUser = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.accepted().body(savedUser);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
