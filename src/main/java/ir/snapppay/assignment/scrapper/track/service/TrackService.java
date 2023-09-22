package ir.snapppay.assignment.scrapper.track.service;


import ir.snapppay.assignment.scrapper.app.security.services.UserDetailsImpl;
import ir.snapppay.assignment.scrapper.notification.service.NotificationService;
import ir.snapppay.assignment.scrapper.process.model.DKDataModel;
import ir.snapppay.assignment.scrapper.track.model.AddUrlDTO;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import ir.snapppay.assignment.scrapper.track.repository.TrackRepository;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TrackService {
    final private TrackRepository trackRepository;
    final private NotificationService notificationService;
    @Value("${config.crawl.interval.ms}")
    int CRAWL_INTERVAL_MS;
    @Value("${config.max.concurrency}")
    int MAX_CONCURRENCY;
    public TrackService(TrackRepository trackRepository, NotificationService notificationService) {
        this.trackRepository = trackRepository;
        this.notificationService = notificationService;
    }

    public ResponseDTO addURL(AddUrlDTO dto) {
        //Extract user details from JWT token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        //Fetch user details from DB
        TrackDomain track = trackRepository.findTrackDomainByUrl(dto.getUrl());
        //Check if URL already exists
        if (track != null) {
            //Add userId if user does not exist in user list
            if (!track.getUserIds().contains(principal.getId()))
                trackRepository.updateTrackByAddUserId(track.getId(), principal.getId());
            else
                return new ResponseDTO("URL already saved!");
        } else {
            //Create and save new track
            trackRepository.save(
                    TrackDomain.builder()
                            .url(dto.getUrl())
                            .userIds(List.of(principal.getId()))
                            .productId(extractProductId(dto.getUrl()))
                            .nextCrawlDate(new Date())
                            .build()
            );

        }
        System.out.println(principal.getId());
        return new ResponseDTO("URL saved successfully!");
    }

    private String extractProductId(String url) {
        return url.substring(url.indexOf("/dkp-") + 5, url.indexOf("/", url.indexOf("/dkp-") + 1));
    }

    public List<TrackDomain> fetchEligibleTracks() {
        Page<TrackDomain> eligibleTracks = trackRepository.findAllByNextCrawlDateBefore(new Date(), PageRequest
                .of(0, MAX_CONCURRENCY));
        return eligibleTracks.getContent();
    }

    @Transactional
    public void updateTrack(TrackDomain track, DKDataModel body) {
        System.out.println("Before:");
        System.out.println(track.toString());
        //Generate nextCrawlDate based on config interval
        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(track.getNextCrawlDate().getTime() + CRAWL_INTERVAL_MS);
        //Check if this is first crawl, so set the currentPrice as the lastPrice
        if (track.getCurrentPrice() == null || track.getLastPrice() == null) {
            trackRepository.updateTrackByProcessResponse(track.getId(),
                    body.getData().getProduct().getDefault_variant().getPrice().getSelling_price(),
                    body.getData().getProduct().getDefault_variant().getPrice().getSelling_price(),
                    next.getTime()
            );
            //If there is any changes in price, update currentPrice, lastPrice, nextCrawlDate
        } else if (!track.getCurrentPrice().equals(body.getData().getProduct().getDefault_variant().getPrice().getSelling_price())) {
            trackRepository.updateTrackByProcessResponse(track.getId(),
                    body.getData().getProduct().getDefault_variant().getPrice().getSelling_price(),
                    track.getCurrentPrice(),
                    next.getTime()
            );
            notificationService.addNotification(trackRepository.findTrackDomainByUrl(track.getUrl()));
            //If there is no changes in price, just update nextCrawlDate
        } else {
            trackRepository.updateTrackByNextCrawlDate(track.getId(), next.getTime());
        }

        System.out.println("After:");
        System.out.println(trackRepository.findTrackDomainByUrl(track.getUrl()));
    }
}
