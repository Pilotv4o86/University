package org.example.userservice.dto.user;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String username;
    private String password;
}
