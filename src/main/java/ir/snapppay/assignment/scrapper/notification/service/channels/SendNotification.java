package ir.snapppay.assignment.scrapper.notification.service.channels;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;

public interface SendNotification {
    void sendMessage(NotificationDomain notification);
}
