package com.springRest.DocumentUploader.repositories;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Page<Notification> findAllByUser(User user, Pageable pageable);

    Notification findByUserAndId(User user, UUID id);
}
