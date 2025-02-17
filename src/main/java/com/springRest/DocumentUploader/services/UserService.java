package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.UserDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserDTO register(UserDTO user);

    Optional<User> getUserEntityByUsername(String username);

    Optional<User> getUserEntityById(UUID id);

    Optional<UserDTO> getUserByUsername(String username);

    Optional<UserDTO> getUserById(UUID id);

}
