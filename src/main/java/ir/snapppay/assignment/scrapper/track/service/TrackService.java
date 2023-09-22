package ir.snapppay.assignment.scrapper.track.service;


import ir.snapppay.assignment.scrapper.app.security.services.UserDetailsImpl;
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
    @Value("${config.crawl.interval.ms}")
    int CRAWL_INTERVAL_MS;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
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
                            .productId(dto.getUrl().substring(dto.getUrl().indexOf("/dkp-") + 5, dto.getUrl().indexOf("/", dto.getUrl().indexOf("/dkp-") + 1)))
                            .nextCrawlDate(new Date())
                            .build()
            );

        }
        System.out.println(principal.getId());
        return new ResponseDTO("URL saved successfully!");
    }

    public List<TrackDomain> fetchEligibleTracks() {
        Page<TrackDomain> eligibleTracks = trackRepository.findAllByNextCrawlDateBefore(new Date(), PageRequest
                .of(0, 100));
        return eligibleTracks.getContent();
    }

    @Transactional
    public void updateTrack(TrackDomain track, DKDataModel body) {
        //Generate nextCrawlDate based on config interval
        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(new Date().getTime() + CRAWL_INTERVAL_MS);
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
            //If there is no changes in price, just update nextCrawlDate
        } else {
            trackRepository.updateTrackByNextCrawlDate(track.getId(), next.getTime());
        }
    }
}
