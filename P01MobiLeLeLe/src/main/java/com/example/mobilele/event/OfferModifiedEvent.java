package com.example.mobilele.event;

import org.springframework.context.ApplicationEvent;

public class OfferModifiedEvent extends ApplicationEvent {
    private String offerId;
    private String action;

    public OfferModifiedEvent(Object source, String offerId, String action) {
        super(source);
        this.offerId = offerId;
        this.action = action;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getAction() {
        return action;
    }
}
