package com.loz.dao.model;

import com.loz.dao.feed.facebook.event.Place;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by loz on 23/02/15.
 */
@Entity
@Table(name = "GALLERY")
public class GalleryData implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;
    @Column(name = "PICTURE")
    private String picture;
    @Column(name = "THUMB")
    private String thumb;
    @Column(name = "USER")
    private String user;
    @Column(name = "CAPTION")
    private String caption;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "MODERATED")
    private boolean moderated;

    public GalleryData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture.replaceFirst("http://", "https://");
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getThumb() {
        return thumb.replaceFirst("http://", "https://");
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }
}
