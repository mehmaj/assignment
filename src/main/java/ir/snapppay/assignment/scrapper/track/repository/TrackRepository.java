package ir.snapppay.assignment.scrapper.track.repository;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<TrackDomain, String>,TrackRepositoryCustom {
    TrackDomain findTrackDomainByUrl(String url);
}
