package ir.snapppay.assignment.scrapper.track.repository;

import java.util.Date;

public interface TrackRepositoryCustom {
    void updateTrackByAddUserId(String trackId,String userId);
    void updateTrackByProcessResponse(String trackId,long currentPrice,long lastPrice, Date nextCrawlDate);
    void updateTrackByNextCrawlDate(String trackId, Date nextCrawlDate);

}
