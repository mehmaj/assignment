package ir.snapppay.assignment.scrapper.crawl;

import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import ir.snapppay.assignment.scrapper.track.repository.TrackRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Crawler implements CommandLineRunner {
    final private TrackRepository trackRepository;
    private RestTemplate restTemplate;
    HttpEntity<Object> request;

    @Value("${config.dgkp.url}")
    private String DGKP_URL;
    @Value("${config.user.agent}")
    private String USER_AGENT;

    public Crawler(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
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

    public String crawl(TrackDomain track) {
        String url = String.format(DGKP_URL, track.getProductId());
        ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        crawl(trackRepository.findTrackDomainByUrl("https://www.digikala.com/product/dkp-9140230/%D9%87%D8%AF%D8%B3%D8%AA-%D9%85%D8%AE%D8%B5%D9%88%D8%B5-%D8%A8%D8%A7%D8%B2%DB%8C-%D8%AA%D8%B3%DA%A9%D9%88-%D9%85%D8%AF%D9%84-th-5124/"));
    }
}
