package com.springRest.DocumentUploader.models;

import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
