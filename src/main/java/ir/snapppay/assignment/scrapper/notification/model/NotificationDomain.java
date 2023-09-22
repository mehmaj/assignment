package ir.snapppay.assignment.scrapper.notification.model;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("notifications")
@Data
@Builder
public class NotificationDomain {
    @Id
    private String id;

    @CreatedDate
    private Date createdDate;

    boolean sent;

    String message;

    @DBRef
    private TrackDomain trackDomain;
}
