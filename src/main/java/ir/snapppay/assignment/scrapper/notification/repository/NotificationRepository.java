package ir.snapppay.assignment.scrapper.notification.repository;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository  extends MongoRepository<NotificationDomain,String> {
}
