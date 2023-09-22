package ir.snapppay.assignment.scrapper.notification.service;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import ir.snapppay.assignment.scrapper.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements SendNotification {
    final private NotificationService notificationService;
    final private UserService userService;

    public EmailNotification(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @Override
    public void sendMessage(NotificationDomain notification) {
        notification.getTrackDomain().getUserIds().forEach(id -> {
            UserDomain user = userService.findById(id);
            System.out.println("message:" + notification.getMessage());
            System.out.println("to:" + user.getEmail());
        });
        notificationService.markAsSent(notification);
    }
}
