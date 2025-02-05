package org.example.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.user.CreateUserRequest;
import org.example.userservice.error.ErrorMessages;
import org.example.userservice.exception.DuplicateResourceException;
import org.example.userservice.exception.ResourceNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.dto.user.UserResponse;
import org.example.userservice.dto.user.UpdateUserRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponse register(CreateUserRequest createUserRequest) {
        checkUserExistenceByUsernameAndThrow(createUserRequest.getUsername());

        User user = userMapper.fromCreatetoUser(createUserRequest);
        user.setPassword(BCrypt.hashpw(createUserRequest.getPassword(), BCrypt.gensalt()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User existingUser = findUserByIdOrThrow(id);

        userMapper.updateUserFromRequest(updateUserRequest, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse getUserById(Long id) {
        User user = findUserByIdOrThrow(id);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        checkUserExistenceByUsernameOrThrow(username);
        //System.out.println("avbhlaebrhliebhiaebrhiaebuieauiebui");
        return userMapper.toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found")));
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
