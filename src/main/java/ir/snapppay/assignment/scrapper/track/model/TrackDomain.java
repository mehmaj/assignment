package ir.snapppay.assignment.scrapper.track.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("tracks")
@Data
@Builder
public class TrackDomain {
    @Id
    private String id;

    @Size(max = 500)
    private String url;

    private String productId;

    @NotBlank
    private List<String> userIds;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;
}
