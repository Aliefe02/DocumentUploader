package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.NotificationDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {

    Optional<NotificationDTO> getNotificationById(User user, UUID id);

    Page<NotificationDTO> listNotifications(User user, Integer pageNumber, Integer pageSize);

    NotificationDTO createNotification(Notification notification);
}
