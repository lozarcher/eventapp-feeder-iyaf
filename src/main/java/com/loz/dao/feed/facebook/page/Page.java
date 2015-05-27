package com.loz.dao.feed.facebook.page;

import com.loz.dao.feed.facebook.common.Picture;
import com.loz.dao.feed.facebook.common.Cover;

/**
 * Created by larcher on 13/03/2015.
 */
public class Page {
    private Long id;
    private String name;
    private String about;
    private Cover cover;
    private Picture picture;
    private String link;
    private String phone;
    private String website;
    private boolean isKingstonPound;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public boolean isKingstonPound() {
        return isKingstonPound;
    }

    public void setKingstonPound(boolean isKingstonPound) {
        this.isKingstonPound = isKingstonPound;
    }
}
