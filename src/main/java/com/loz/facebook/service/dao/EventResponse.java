package com.loz.facebook.service.dao;

public class EventResponse {

    private Long id;
    private Events events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }
}
