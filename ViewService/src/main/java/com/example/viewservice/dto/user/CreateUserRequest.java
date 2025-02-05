package com.example.viewservice.dto.user;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private Role role;
}
