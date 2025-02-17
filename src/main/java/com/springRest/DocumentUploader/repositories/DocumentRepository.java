package com.springRest.DocumentUploader.repositories;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.DocumentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Page<Document> findAllByUser(User user, Pageable pageable);

    Optional<Document> findByUserAndId(User user, UUID id);
}
