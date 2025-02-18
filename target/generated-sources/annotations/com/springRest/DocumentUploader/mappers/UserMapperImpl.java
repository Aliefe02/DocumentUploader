package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T00:33:21+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDTOToUser(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.version( dto.getVersion() );
        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        user.createdAt( dto.getCreatedAt() );
        user.updatedAt( dto.getUpdatedAt() );

        return user.build();
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.version( user.getVersion() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );
        userDTO.username( user.getUsername() );
        userDTO.password( user.getPassword() );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.updatedAt( user.getUpdatedAt() );

        return userDTO.build();
    }
}
