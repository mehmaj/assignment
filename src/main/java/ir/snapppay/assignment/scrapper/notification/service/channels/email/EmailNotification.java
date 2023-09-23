package ir.snapppay.assignment.scrapper.notification.service.channels.email;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import ir.snapppay.assignment.scrapper.notification.service.NotificationService;
import ir.snapppay.assignment.scrapper.notification.service.channels.SendNotification;
import ir.snapppay.assignment.scrapper.notification.service.channels.email.gmail.GmailServiceImpl;
import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import ir.snapppay.assignment.scrapper.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements SendNotification {
    final private NotificationService notificationService;
    final private UserService userService;
    final private GmailServiceImpl emailService;

    public EmailNotification(NotificationService notificationService, UserService userService, GmailServiceImpl emailService) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void sendMessage(NotificationDomain notification) {
        notification.getTrackDomain().getUserIds().forEach(id -> {
            UserDomain user = userService.findById(id);
            System.out.println("message:" + user.getEmail());
            System.out.println("to:" + user.getEmail());
            emailService.sendSimpleMail(user.getEmail(), notification.getMessage());
        });
        notificationService.markAsSent(notification);
    }
}
