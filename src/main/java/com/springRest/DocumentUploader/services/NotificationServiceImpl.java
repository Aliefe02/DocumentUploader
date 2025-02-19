package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.mappers.NotificationMapper;
import com.springRest.DocumentUploader.models.NotificationDTO;
import com.springRest.DocumentUploader.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.springRest.DocumentUploader.services.DocumentServiceImpl.buildPageRequest;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
     @Autowired
     private NotificationRepository notificationRepository;

     @Autowired
     private NotificationMapper notificationMapper;

    @Override
    public Optional<NotificationDTO> getNotificationById(User user, UUID id) {
        return Optional.of(notificationMapper.notificationToNotificationDto(notificationRepository.findByUserAndId(user, id)));
    }

    @Override
    public Page<NotificationDTO> listNotifications(User user, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return notificationRepository.findAllByUser(user, pageRequest).map(notificationMapper::notificationToNotificationDto);
    }

    @Override
    public NotificationDTO createNotification(Notification notification) {
        return notificationMapper.notificationToNotificationDto(notificationRepository.save(notification));
    }
}
