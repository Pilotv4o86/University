package org.example.userservice.mapper;

import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.dto.UpdateUserRequest;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public UserResponse toUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User toUserResponse(UserResponse userById) {
        return modelMapper.map(userById, User.class);
    }

    public User fromCreatetoUser(UserResponse userByUsername) {
        return modelMapper.map(userByUsername, User.class);
    }

    public void updateUserFromRequest(UpdateUserRequest updateUserRequest, User existingUser) {
        modelMapper.map(updateUserRequest, existingUser);
    }

    public User fromCreatetoUser(CreateUserRequest createUserRequest) {
        return modelMapper.map(createUserRequest, User.class);
    }
}
