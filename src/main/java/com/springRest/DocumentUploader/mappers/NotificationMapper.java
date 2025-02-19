package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.models.NotificationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    Notification notificationDtoToNotification(NotificationDTO dto);

    NotificationDTO notificationToNotificationDto(Notification notification);

}
