package com.example.viewservice.client;

import com.example.viewservice.dto.user.CreateUserRequest;
import com.example.viewservice.dto.user.LoginUserRequest;
import com.example.viewservice.dto.user.UpdateUserRequest;
import com.example.viewservice.dto.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/university/users/{id}")
    UserResponse getUserById(@PathVariable Long id);
    @PostMapping("/university/users/register")
    UserResponse register(@RequestBody CreateUserRequest createUserRequest);
    @PutMapping("/university/users/{id}/update")
    UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest);
    @DeleteMapping("/university/users/{id}/delete")
    UserResponse deleteUser(@PathVariable Long id);
    @GetMapping("/university/users/all-users")
    List<UserResponse> getAllUsers();

    @PostMapping("/university/login")
    UserResponse login(@RequestBody LoginUserRequest user);
}
