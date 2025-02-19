package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.models.DocumentDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T16:16:41+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public Document documentDtoToDocument(DocumentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Document.DocumentBuilder document = Document.builder();

        document.description( dto.getDescription() );
        document.id( dto.getId() );
        document.fileName( dto.getFileName() );
        document.contentType( dto.getContentType() );
        document.createdAt( dto.getCreatedAt() );
        document.updatedAt( dto.getUpdatedAt() );
        document.notifyAt( dto.getNotifyAt() );

        return document.build();
    }

    @Override
    public DocumentDTO documentToDocumentDto(Document document) {
        if ( document == null ) {
            return null;
        }

        DocumentDTO.DocumentDTOBuilder documentDTO = DocumentDTO.builder();

        documentDTO.description( document.getDescription() );
        documentDTO.id( document.getId() );
        documentDTO.fileName( document.getFileName() );
        documentDTO.contentType( document.getContentType() );
        documentDTO.createdAt( document.getCreatedAt() );
        documentDTO.updatedAt( document.getUpdatedAt() );
        documentDTO.notifyAt( document.getNotifyAt() );

        return documentDTO.build();
    }
}
