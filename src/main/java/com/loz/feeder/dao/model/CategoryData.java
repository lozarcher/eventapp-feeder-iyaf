package com.loz.feeder.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class CategoryData implements Serializable {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IMAGE")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "EVENT_CATEGORY",
            joinColumns = {            @JoinColumn(
                    name = "CATEGORYID"
            )},
            inverseJoinColumns = {            @JoinColumn(
                    name = "EVENTID"
            )}
    )
    @JsonIgnore
    private Set<EventData> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<EventData> getEvents() {
        return events;
    }

    public void setEvents(Set<EventData> events) {
        this.events = events;
    }
}
