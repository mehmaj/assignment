package ir.snapppay.assignment.scrapper.track.repository;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

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

    @Override
    public void updateTrackByProcessResponse(String trackId, long currentPrice, long lastPrice, Date nextCrawlDate) {
        Update update = new Update();
        update.set("currentPrice", currentPrice);
        update.set("lastPrice", lastPrice);
        update.set("nextCrawlDate", nextCrawlDate);
        mongoOperations.updateFirst(
                new Query().addCriteria(Criteria.where("id").is(trackId)),
                update,
                TrackDomain.class);
    }

    @Override
    public void updateTrackByNextCrawlDate(String trackId, Date nextCrawlDate) {
        mongoOperations.updateFirst(
                new Query().addCriteria(Criteria.where("id").is(trackId)),
                new Update().set("nextCrawlDate", nextCrawlDate),
                TrackDomain.class);
    }
}
