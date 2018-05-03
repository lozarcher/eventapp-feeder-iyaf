package com.loz.feeder.dao.feed.facebook.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.loz.feeder.dao.feed.facebook.common.Cover;
import com.loz.feeder.dao.feed.facebook.common.Picture;

import java.util.Date;
import java.util.List;

public class Event {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long id;
    private String name;
    private String description;
    private Cover cover;
    private Date start_time;
    private Date end_time;
    private String location;
    private Place place;
    private String ticket_uri;
    private Long eventId;
    private List<EventTime> eventTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @JsonProperty("event_times")
    public List<EventTime> getEventTimes() {
        return eventTimes;
    }

    public void setEventTimes(List<EventTime> eventTimes) {
        this.eventTimes = eventTimes;
    }

    public String getTicket_uri() {
        return ticket_uri;
    }

    public void setTicket_uri(String ticket_uri) {
        this.ticket_uri = ticket_uri;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Event createDuplicateEvent(Long id, Date startTime, Date endTime) {
        Event newEvent = new Event();
        newEvent.setId(id);
        newEvent.setName(this.getName());
        newEvent.setDescription(this.getDescription());
        newEvent.setCover(this.getCover());
        newEvent.setLocation(this.getLocation());
        newEvent.setPlace(this.getPlace());
        newEvent.setTicket_uri(this.getTicket_uri());
        newEvent.setEventTimes(this.getEventTimes());
        newEvent.setStart_time(startTime);
        newEvent.setEnd_time(endTime);
        newEvent.setEventId(this.getEventId());
        return newEvent;
    }
}

