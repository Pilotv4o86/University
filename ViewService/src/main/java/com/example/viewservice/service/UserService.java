package com.example.viewservice.service;

import com.example.viewservice.client.UserClient;
import com.example.viewservice.dto.user.CreateUserRequest;
import com.example.viewservice.dto.user.LoginUserRequest;
import com.example.viewservice.dto.user.UpdateUserRequest;
import com.example.viewservice.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserClient userClient;

    public UserResponse register(CreateUserRequest createUserRequest) {
        return userClient.register(createUserRequest);
    }
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }
    public List<UserResponse> getAllUsers() {
        return userClient.getAllUsers();
    }

    public UserResponse deleteUser(Long id) {
       return userClient.deleteUser(id);
    }
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        return userClient.updateUser(id, updateUserRequest);
    }

    public UserResponse login(LoginUserRequest loginUserRequest) {
        return userClient.login(loginUserRequest);
    }
}
