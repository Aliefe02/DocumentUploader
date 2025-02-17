package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.models.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    Document documentDtoToDocument(DocumentDTO dto);

    DocumentDTO documentToDocumentDto(Document document);
}
