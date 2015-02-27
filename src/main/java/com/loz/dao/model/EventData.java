package com.loz.dao.model;

import com.loz.dao.feed.facebook.Cover;
import com.loz.dao.feed.facebook.Event;

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

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "LOCATION")
    private String location;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public EventData(Event event) {
        new EventData();
        this.setId(event.getId());
        this.setName(event.getName());
        this.setDescription(event.getDescription());
        this.setLocation(event.getLocation());
        Cover cover = event.getCover();
        if (cover != null) {
            this.setCoverUrl(cover.getSource());
        }
        this.setStartTime(event.getStart_time());
        this.setEndTime(event.getEnd_time());
        VenueData venueData = new VenueData(event.getVenue());
        this.setVenue(venueData);
    }

}