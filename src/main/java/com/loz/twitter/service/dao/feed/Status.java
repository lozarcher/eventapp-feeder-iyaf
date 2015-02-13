package com.loz.twitter.service.dao.feed;

/**
 * Created by loz on 05/02/15.
 */
public class Status {
    private String id_str;
    private String text;
    private User user;

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
