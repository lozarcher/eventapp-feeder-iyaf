package com.loz.dao.model;

import com.loz.dao.feed.facebook.common.Cover;
import com.loz.dao.feed.facebook.common.Picture;
import com.loz.dao.feed.facebook.event.Event;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INFO")
public class InfoData {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "PICTURE")
    private String picture;

    @Column(name = "THUMB")
    private String thumb;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}