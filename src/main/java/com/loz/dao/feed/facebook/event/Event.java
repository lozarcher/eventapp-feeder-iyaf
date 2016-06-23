package com.loz.dao.feed.facebook.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loz.dao.feed.facebook.common.Cover;
import com.loz.dao.feed.facebook.common.Picture;

import java.util.Date;

public class Event {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Long id;
    private String name;
    private String description;
    private Cover cover;
    private Picture picture;
    private Date start_time;
    private Date end_time;
    private String location;
    private Place place;
    private String ticket_uri;

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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getTicket_uri() {
        return ticket_uri;
    }

    public void setTicket_uri(String ticket_uri) {
        this.ticket_uri = ticket_uri;
    }
}

