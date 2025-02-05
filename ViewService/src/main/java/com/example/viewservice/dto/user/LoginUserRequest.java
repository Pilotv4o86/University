package com.example.viewservice.dto.user;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String username;
    private String password;
}
