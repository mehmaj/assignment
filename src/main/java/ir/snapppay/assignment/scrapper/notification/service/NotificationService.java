package ir.snapppay.assignment.scrapper.notification.service;

import ir.snapppay.assignment.scrapper.notification.model.NotificationDomain;
import ir.snapppay.assignment.scrapper.notification.repository.NotificationRepository;
import ir.snapppay.assignment.scrapper.track.model.TrackDomain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    final private NotificationRepository notificationRepository;
    @Value("${message.notification.increase.template}")
    private String INCREASED_TEMPLATE;
    @Value("${message.notification.decrease.template}")
    private String DECREASED_TEMPLATE;
    @Value("${config.notification.eligible.page.size}")
    private int ELIGIBLE_PAGE_SIZE;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void addNotification(TrackDomain track) {
        notificationRepository.save(NotificationDomain.builder()
                .trackDomain(track)
                .message(generateMessage(track))
                .build());
    }

    private String generateMessage(TrackDomain track) {
        if (track.getCurrentPrice() > track.getLastPrice())
            return String.format(INCREASED_TEMPLATE, track.getUrl(), ((track.getCurrentPrice() - track.getLastPrice()) / (float) track.getLastPrice()));
        else
            return String.format(DECREASED_TEMPLATE, track.getUrl(), ((track.getLastPrice() - track.getCurrentPrice()) / (float) track.getLastPrice()));
    }

    public List<NotificationDomain> fetchEligibleNotifications() {
        Page<NotificationDomain> eligibleNotifications = notificationRepository.findAllByCreatedDateBeforeAndSentIsFalse(new Date(), PageRequest
                .of(0, ELIGIBLE_PAGE_SIZE));
        return eligibleNotifications.getContent();
    }

    public void markAsSent(NotificationDomain notification) {
        notificationRepository.updateNotificationAsSent(notification.getId());
    }
}
