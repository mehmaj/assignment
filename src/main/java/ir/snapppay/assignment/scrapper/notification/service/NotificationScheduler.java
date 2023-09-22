package ir.snapppay.assignment.scrapper.notification.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {
    final private NotificationService notificationService;
    final private EmailNotification emailNotification;

    public NotificationScheduler(NotificationService notificationService, EmailNotification emailNotification) {
        this.notificationService = notificationService;
        this.emailNotification = emailNotification;
    }


    //every 1 m scheduler checks if there is any eligible notification record, so call sendMessage
    @Scheduled(fixedRate = 60000)
    public void scheduleTrackProcess() {
        System.out.println("scheduler running...");
        notificationService.fetchEligibleNotifications().forEach(emailNotification::sendMessage);
    }
}
