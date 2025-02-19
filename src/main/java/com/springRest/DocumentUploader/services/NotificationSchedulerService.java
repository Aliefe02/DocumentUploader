package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.BackgroundProcesses.NotificationJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
public class NotificationSchedulerService {


    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void initialize() throws SchedulerException {
        // Ensure that the scheduler is started
        if (!scheduler.isStarted()) {
            scheduler.start();
            System.out.println("Scheduler started.");
        } else {
            System.out.println("Scheduler is already started.");
        }
    }

    public void scheduleNotification(UUID documentId, UUID userId, String description, LocalDateTime notifyAt) {
        try {
            Date triggerTime = Date.from(notifyAt.atZone(ZoneId.systemDefault()).toInstant());

            JobDetail jobDetail = JobBuilder.newJob(NotificationJob.class)
                    .withIdentity("notification_" + documentId, "notifications")
                    .usingJobData("description", description)
                    .usingJobData("userId", userId.toString())
                    .usingJobData("documentId", documentId.toString())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_" + documentId, "notifications")
                    .startAt(triggerTime)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("New notification job has been created. Document: " + documentId + " datetime: " + trigger.getStartTime());

        } catch (SchedulerException e) {
            throw new RuntimeException("Error scheduling notification job for document: " + documentId, e);
        }
    }


    public void deleteNotificationJob(UUID documentId){
        try{
            JobKey jobKey = new JobKey("notification_"+documentId, "notifications");
            if (scheduler.checkExists(jobKey)){
                scheduler.deleteJob(jobKey);
                System.out.println("Notification job deleted for document: " + documentId);
            }
        } catch (SchedulerException e){
            throw new RuntimeException("Error deleting notification job", e);
        }
    }
}





















