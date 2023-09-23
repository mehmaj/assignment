package ir.snapppay.assignment.scrapper.notification.service.channels.email.gmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailServiceImpl {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    @Value("${message.notification.email.title}")
    private String TITLE;

    public GmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail(String recipient, String body) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(body);
            mailMessage.setSubject(TITLE);

            javaMailSender.send(mailMessage);
            System.out.println("Email was sent successfully!");
        } catch (Exception e) {
            System.out.println("Error in sending email:" + e.getMessage());
        }
    }
}
