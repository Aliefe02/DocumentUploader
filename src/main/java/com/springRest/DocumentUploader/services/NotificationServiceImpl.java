package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.mappers.NotificationMapper;
import com.springRest.DocumentUploader.models.NotificationDTO;
import com.springRest.DocumentUploader.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
     @Autowired
     private NotificationRepository notificationRepository;

     @Autowired
     private NotificationMapper notificationMapper;

    @Override
    public Optional<NotificationDTO> getNotificationById(User user, UUID id) {
        return Optional.empty();
    }

    @Override
    public Page<NotificationDTO> listNotifications(User user, Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public NotificationDTO createNotification(Notification notification) {
        return notificationMapper.notificationToNotificationDto(notificationRepository.save(notification));
    }
}
