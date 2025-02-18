package com.springRest.DocumentUploader.models;

import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private UUID id;

    private String fileName;

    private String contentType;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
