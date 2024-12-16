package org.example.authservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String token;
}
