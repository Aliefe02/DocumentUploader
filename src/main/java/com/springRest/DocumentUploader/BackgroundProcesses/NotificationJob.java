package com.springRest.DocumentUploader.BackgroundProcesses;

import com.springRest.DocumentUploader.controllers.NotFoundException;
import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.Notification;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.NotificationDTO;
import com.springRest.DocumentUploader.repositories.NotificationRepository;
import com.springRest.DocumentUploader.services.DocumentService;
import com.springRest.DocumentUploader.services.NotificationService;
import com.springRest.DocumentUploader.services.UserService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationJob implements Job {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String description = dataMap.getString("description");
        UUID userId = UUID.fromString(dataMap.getString("userId"));
        UUID documentId = UUID.fromString(dataMap.getString("documentId"));

        User user = userService.getUserEntityById(userId).orElseThrow(NotFoundException::new);
        Document document = documentService.getDocumentEntityById(documentId).orElseThrow(NotFoundException::new);

        Notification notification = Notification.builder()
                .description(description)
                .user(user)
                .document(document)
                .build();

        NotificationDTO savedNotification = notificationService.createNotification(notification);

        System.out.println("New notification is created for " + user.getUsername() + " documentId: "+ documentId);
    }

}














