package com.springRest.DocumentUploader.models;


import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private UUID id;

    private String description;

    private Timestamp createdAt;

    private UUID userId;

    private UUID documentId;

}
