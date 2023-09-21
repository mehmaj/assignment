package ir.snapppay.assignment.scrapper.track.repository;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TrackRepositoryCustomImpl implements TrackRepositoryCustom {
    private final MongoOperations mongoOperations;

    public TrackRepositoryCustomImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void updateTrackByAddUserId(String trackId, String userId) {
        mongoOperations.updateFirst(
                new Query().addCriteria(Criteria.where("id").is(trackId)),
                new Update().push("userIds", userId),
                TrackDomain.class);
    }
}
