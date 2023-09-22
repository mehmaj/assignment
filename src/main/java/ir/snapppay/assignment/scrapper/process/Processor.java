package ir.snapppay.assignment.scrapper.process;

import ir.snapppay.assignment.scrapper.process.model.DKDataModel;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Processor {

    private RestTemplate restTemplate;
    HttpEntity<Object> request;

    @Value("${config.dgkp.url}")
    private String DGKP_URL;
    @Value("${config.user.agent}")
    private String USER_AGENT;


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
    public DKDataModel process(TrackDomain track) {
        String url = String.format(DGKP_URL, track.getProductId());
        ResponseEntity<DKDataModel> result = restTemplate.exchange(url, HttpMethod.GET, request, DKDataModel.class);
        return result.getBody();
    }

}
