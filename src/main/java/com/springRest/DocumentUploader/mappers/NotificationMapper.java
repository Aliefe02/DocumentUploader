package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.models.NotificationDTO;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper
public interface NotificationMapper {
    Notification notificationDtoToNotification(NotificationDTO dto);

    NotificationDTO notificationToNotificationDto(Notification notification);

    default Timestamp map(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    default LocalDateTime map(Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }


}
