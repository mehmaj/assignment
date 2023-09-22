package ir.snapppay.assignment.scrapper.process.service;

import ir.snapppay.assignment.scrapper.track.service.TrackService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    final private TrackService trackService;
    final private Processor processor;

    public Scheduler(TrackService trackService, Processor processor) {
        this.trackService = trackService;
        this.processor = processor;
    }

    //every 30s scheduler checks if there is any eligible track record, so call processor thread
    @Scheduled(fixedRate = 30000)
    public void scheduleTrackProcess() {
        System.out.println("scheduler running...");
        trackService.fetchEligibleTracks().forEach(processor::process);
    }
}
