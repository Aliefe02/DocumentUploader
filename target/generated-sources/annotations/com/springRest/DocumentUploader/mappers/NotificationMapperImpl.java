package com.springRest.DocumentUploader.mappers;

import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.models.NotificationDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T16:16:40+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.6 (Ubuntu)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public Notification notificationDtoToNotification(NotificationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notification.NotificationBuilder notification = Notification.builder();

        notification.id( dto.getId() );
        notification.description( dto.getDescription() );
        notification.createdAt( dto.getCreatedAt() );

        return notification.build();
    }

    @Override
    public NotificationDTO notificationToNotificationDto(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDTO.NotificationDTOBuilder notificationDTO = NotificationDTO.builder();

        notificationDTO.id( notification.getId() );
        notificationDTO.description( notification.getDescription() );
        notificationDTO.createdAt( notification.getCreatedAt() );

        return notificationDTO.build();
    }
}
