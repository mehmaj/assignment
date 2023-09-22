package ir.snapppay.assignment.scrapper.track.repository;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface TrackRepository extends MongoRepository<TrackDomain, String>,TrackRepositoryCustom {
    TrackDomain findTrackDomainByUrl(String url);
    Page<TrackDomain> findAllByNextCrawlDateBefore(Date now, PageRequest pageRequest);
}
