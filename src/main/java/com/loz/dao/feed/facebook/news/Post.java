package com.loz.dao.feed.facebook.news;

import java.util.Date;

/**
 * Created by larcher on 27/05/2015.
 */
public class Post {
    private String id;
    private String message;
    private String picture;
    private String link;
    private String name;
    private String caption;
    private Date created_time;

    public Long getId() {
        String[] ids = id.split("_");
        if (ids.length==2) {
            return Long.parseLong(ids[1]);
        } else {
            return Long.parseLong(ids[0]);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }
}
