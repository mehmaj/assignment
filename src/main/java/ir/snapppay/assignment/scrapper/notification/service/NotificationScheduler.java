package ir.snapppay.assignment.scrapper.notification.service;

import ir.snapppay.assignment.scrapper.notification.service.channels.email.EmailNotification;
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
    @Scheduled(fixedRateString = "${config.notification.scheduler.rate.ms}")
    public void scheduleTrackProcess() {
        System.out.println("Notification Scheduler running...");
        notificationService.fetchEligibleNotifications().forEach(emailNotification::sendMessage);
    }
}
