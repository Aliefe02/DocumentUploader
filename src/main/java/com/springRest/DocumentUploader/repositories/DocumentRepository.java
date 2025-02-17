package com.springRest.DocumentUploader.repositories;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    Page<Document> findAllByUser(User user, Pageable pageable);

    Optional<Document> findByIdAndUser(UUID id, User user);

    boolean existsByIdAndUser(UUID id, User user);
}
