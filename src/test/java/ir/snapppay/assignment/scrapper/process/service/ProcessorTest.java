package ir.snapppay.assignment.scrapper.process.service;

import ir.snapppay.assignment.scrapper.process.model.DKDataModel;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import ir.snapppay.assignment.scrapper.track.service.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@SpringBootTest

class ProcessorTest {
    @Autowired
    Processor processor;

    @MockBean
    TrackService trackService;

    @Test
    void compute() {
        ResponseEntity<DKDataModel> crawl = processor.crawl(TrackDomain.builder()
                .productId("9140230")
                        .nextCrawlDate(new Date())
                .build());
        Assertions.assertNotNull(crawl.getBody());
    }
}