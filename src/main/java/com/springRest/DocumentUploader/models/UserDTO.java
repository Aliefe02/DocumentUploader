package com.springRest.DocumentUploader.models;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    private Integer version;

    private String firstName;
    private String lastName;
    private String username;

    private String password;

    private Timestamp createdAt;
    private Timestamp updatedAt;

}
