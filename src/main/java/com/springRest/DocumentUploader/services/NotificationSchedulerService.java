package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.BackgroundProcesses.NotificationJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Service
public class NotificationSchedulerService {


    @Autowired
    private Scheduler scheduler;

    public void scheduleNotification(UUID documentId, UUID userId, String description, Timestamp notifyAt){
        try {
            Date triggerTime = new Date(notifyAt.getTime());

            JobDetail jobDetail = JobBuilder.newJob(NotificationJob.class)
                    .withIdentity("notification_" + documentId, "notifications")
                    .usingJobData("description", description)
                    .usingJobData("userId", userId.toString())
                    .usingJobData("documentId", documentId.toString())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger_" + documentId, "notifications")
                    .startAt(triggerTime)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("Error scheduling notification job for document: "+documentId, e);
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





















