package org.example.userservice.dto.user;

import lombok.Data;
import org.example.userservice.model.Role;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private Role role;
}
