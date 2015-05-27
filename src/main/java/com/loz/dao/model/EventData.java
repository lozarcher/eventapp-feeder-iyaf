package com.loz.dao.model;

import com.loz.dao.feed.facebook.Cover;
import com.loz.dao.feed.facebook.Event;
import com.loz.dao.feed.facebook.Picture;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EVENT")
public class EventData {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COVER_URL")
    private String coverUrl;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "LOCATION")
    private String location;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    private VenueData venue;

    public EventData() {
    }

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public VenueData getVenue() {
        return venue;
    }

    public void setVenue(VenueData venue) {
        this.venue = venue;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setDataFromEvent(Event event) {
        this.setId(event.getId());
        this.setName(event.getName());
        this.setDescription(event.getDescription());
        this.setLocation(event.getPlace().getName());
        Cover cover = event.getCover();
        if (cover != null) {
            this.setCoverUrl(cover.getSource());
        }
        Picture picture = event.getPicture();
        if (picture != null) {
            this.setProfileUrl(picture.getData().getUrl());
        }
        this.setStartTime(event.getStart_time());
        this.setEndTime(event.getEnd_time());
        if (event.getPlace() == null || event.getPlace().getId() == null) {
            this.setVenue(null);
        } else {
            VenueData venueData = new VenueData(event.getPlace());
            this.setVenue(venueData);
        }
    }

}