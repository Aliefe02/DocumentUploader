package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.mappers.UserMapper;
import com.springRest.DocumentUploader.models.UserDTO;
import com.springRest.DocumentUploader.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserEntityByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserEntityById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username){
        return Optional.ofNullable(userMapper.userToUserDTO(userRepository.findByUsername(username).orElse(null)));
    }

    @Override
    public UserDTO register(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        return userMapper.userToUserDTO(userRepository.save(userMapper.userDTOToUser(user)));
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        return Optional.ofNullable(userMapper.userToUserDTO(userRepository.findById(id)
                .orElse(null)));
    }
}
