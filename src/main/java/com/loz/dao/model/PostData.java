package com.loz.dao.model;

import com.loz.dao.feed.facebook.common.Cover;
import com.loz.dao.feed.facebook.common.Picture;
import com.loz.dao.feed.facebook.event.Event;
import com.loz.dao.feed.facebook.news.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by larcher on 27/05/2015.
 */
@Entity
@Table(name = "POST")
public class PostData {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "PICTURE")
    private String pictureUrl;

    @Column(name = "LINK")
    private String link;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CAPTION")
    private String caption;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setDataFromPost(Post post) {
        this.setId(post.getId());
        this.setCreatedDate(post.getCreated_time());
        this.setMessage(post.getMessage());
        this.setCaption(post.getCaption());
        this.setLink(post.getLink());
        this.setPictureUrl(post.getPictureUrl());
        this.setName(post.getName());
   }
}