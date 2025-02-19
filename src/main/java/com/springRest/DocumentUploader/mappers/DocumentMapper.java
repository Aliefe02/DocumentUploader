package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.models.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper
public interface DocumentMapper {
    @Mapping(source = "description", target = "description")
    Document documentDtoToDocument(DocumentDTO dto);

    @Mapping(source = "description", target = "description")
    DocumentDTO documentToDocumentDto(Document document);

    default Timestamp map(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    default LocalDateTime map(Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }
}