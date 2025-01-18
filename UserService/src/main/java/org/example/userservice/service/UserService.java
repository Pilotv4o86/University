package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.error.ErrorMessages;
import org.example.userservice.exception.DuplicateResourceException;
import org.example.userservice.exception.ResourceNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.dto.UserResponse;
import org.example.userservice.model.Role;
import org.example.userservice.dto.UpdateUserRequest;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(CreateUserRequest createUserRequest) {
        checkUserExistenceByUsernameAndThrow(createUserRequest.getUsername());

        User user = userMapper.fromCreatetoUser(createUserRequest);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRoles(Set.of(Role.ROLE_STUDENT));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User existingUser = findUserByIdOrThrow(id);

        userMapper.updateUserFromRequest(updateUserRequest, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponse(updatedUser);
    }

    public void disableUser(Long id) {
        User user = findUserByIdOrThrow(id);
        userRepository.save(user);
    }

    public UserResponse getUserById(Long id) {
        User user = findUserByIdOrThrow(id);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        checkUserExistenceByUsernameOrThrow(username);
        //System.out.println("avbhlaebrhliebhiaebrhiaebuieauiebui");
        return userMapper.toUserResponse(userRepository.findByUsername(username));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    //------------------------------------------------------
    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "User", id)));
    }

    private boolean checkUserExistenceByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    private void checkUserExistenceByUsernameAndThrow(String login) {
        if (checkUserExistenceByUsername(login)) {
            throw new DuplicateResourceException(
                    String.format(ErrorMessages.DUPLICATE_RESOURCE_MESSAGE, "User", "username"));
        }
    }

    private void checkUserExistenceByUsernameOrThrow(String username) {
        if (!checkUserExistenceByUsername(username)) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_FIELD_MESSAGE, "User", "username"));
        }
    }
}
