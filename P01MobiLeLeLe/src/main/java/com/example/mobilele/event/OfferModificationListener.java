package com.example.mobilele.event;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class OfferModificationListener {
    private final JavaMailSender mailSender;

    public OfferModificationListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    public void handleOfferModifiedEvent(OfferModifiedEvent event) {
        sendModificationEmail(event.getOfferId(), event.getAction());
    }

    private void sendModificationEmail(String offerId, String action) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("....");
        message.setTo(".....");
        message.setSubject("Offer Modification Notification");
        message.setText("The offer with ID: " + offerId + " has been " + action + ".");

        mailSender.send(message);
    }
}
