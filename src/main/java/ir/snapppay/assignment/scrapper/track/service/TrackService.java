package ir.snapppay.assignment.scrapper.track.service;


import ir.snapppay.assignment.scrapper.app.security.services.UserDetailsImpl;
import ir.snapppay.assignment.scrapper.track.model.AddUrlDTO;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import ir.snapppay.assignment.scrapper.track.repository.TrackRepository;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrackService {
    final private TrackRepository trackRepository;

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
}
