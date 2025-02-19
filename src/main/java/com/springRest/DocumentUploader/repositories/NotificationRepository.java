package com.springRest.DocumentUploader.repositories;

import com.springRest.DocumentUploader.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
