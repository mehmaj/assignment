package ir.snapppay.assignment.scrapper.process.service;

import ir.snapppay.assignment.scrapper.process.model.DKDataModel;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import ir.snapppay.assignment.scrapper.track.service.TrackService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class Processor {
    final private TrackService trackService;
    private RestTemplate restTemplate;
    HttpEntity<Object> request;

    @Value("${config.dgkp.url}")
    private String DGKP_URL;
    @Value("${config.user.agent}")
    private String USER_AGENT;

    public Processor(TrackService trackService) {
        this.trackService = trackService;
    }


    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        HttpHeaders headers;
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", USER_AGENT);
        request = new HttpEntity<>(headers);
    }

    @Async("processExecutor")
    public void process(TrackDomain track) {
        System.out.println("Process started:"+Thread.currentThread().getName());
        System.out.println(new Date());
        //Set url based on productId
        String url = String.format(DGKP_URL, track.getProductId());
        //Crawl and parse URL
        ResponseEntity<DKDataModel> result = restTemplate.exchange(url, HttpMethod.GET, request, DKDataModel.class);
        //Update track based on crawled and parsed data
        trackService.updateTrack(track, result.getBody());
    }

}
