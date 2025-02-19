package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.UserDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface UserMapper {
    User userDTOToUser(UserDTO dto);

    UserDTO userToUserDTO(User user);
    default Timestamp map(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    default LocalDateTime map(Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }
}
