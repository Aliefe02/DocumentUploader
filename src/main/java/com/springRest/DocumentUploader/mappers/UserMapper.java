package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.UserDTO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    User userDTOToUser(UserDTO dto);

    UserDTO userToUserDTO(User user);
}
