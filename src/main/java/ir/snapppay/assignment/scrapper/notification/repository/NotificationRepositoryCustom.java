package ir.snapppay.assignment.scrapper.notification.repository;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface NotificationRepositoryCustom {
    void updateNotificationAsSent(String notificationId);
}
