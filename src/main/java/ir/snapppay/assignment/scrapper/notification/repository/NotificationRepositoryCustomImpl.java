package ir.snapppay.assignment.scrapper.notification.repository;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
    final private MongoOperations mongoOperations;

    public NotificationRepositoryCustomImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void updateNotificationAsSent(String notificationId) {
        mongoOperations.updateFirst(
                new Query().addCriteria(Criteria.where("id").is(notificationId)),
                new Update().set("sent", true),
                NotificationDomain.class);
    }
}
