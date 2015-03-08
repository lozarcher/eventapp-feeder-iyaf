package com.loz.dao.model;

import com.loz.dao.feed.facebook.Venue;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by loz on 23/02/15.
 */
@Entity
@Table(name = "VENUE")
public class VenueData implements Serializable{
    @Id
    @Column(name = "ID", unique = true)
    private Long id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "POSTCODE")
    private String postcode;
    @Column(name = "LATITUDE")
    private Double latitude;
    @Column(name = "LONGITUDE")
    private Double longitude;
    @Column(name = "PHONE")
    private String phone;
    @Column (name = "WEBSITE")
    private String website;

    @Transient
    private String location;

    public VenueData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String town) {
        this.city = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public VenueData(Venue venue) {
        new VenueData();
        this.setId(venue.getId());
        this.setStreet(venue.getStreet());
        this.setCity(venue.getCity());
        this.setPostcode(venue.getPostcode());
        this.setLatitude(venue.getLatitude());
        this.setLongitude(venue.getLongitude());
        this.setWebsite(venue.getWebsite());
        this.setPhone(venue.getPhone());
    }
}
