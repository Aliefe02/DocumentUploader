package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.models.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DocumentMapper {
    @Mapping(source = "description", target = "description")
    Document documentDtoToDocument(DocumentDTO dto);

    @Mapping(source = "description", target = "description")
    DocumentDTO documentToDocumentDto(Document document);
}