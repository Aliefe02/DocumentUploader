package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.DocumentDTO;
import com.springRest.DocumentUploader.models.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO uploadDocument(DocumentDTO document, User user);

    Optional<DocumentDTO> getDocumentByUserAndId(UUID documentId, User user);

    Page<Document> listDocuments(User user, Integer pageNumber, Integer pageSize);

    boolean deleteById(UUID documentId);
}
