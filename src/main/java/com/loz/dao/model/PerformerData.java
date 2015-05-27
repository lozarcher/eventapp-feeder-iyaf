package com.loz.dao.model;

import com.loz.dao.feed.facebook.Page;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by loz on 23/02/15.
 */
@Entity
@Table(name = "PERFORMER")
public class PerformerData implements Serializable{
    @Id
    @Column(name = "ID", unique = true)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ABOUT")
    private String about;
    @Column(name = "COVER_IMG")
    private String coverImg;
    @Column(name = "LINK")
    private String link;
    @Column(name = "PROFILE_IMG")
    private String profileImg;
    @Column(name = "WEBSITE")
    private String website;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "KPOUND")
    private boolean isKingstonPound;

    public PerformerData() {
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isKingstonPound() {
        return isKingstonPound;
    }

    public void setKingstonPound(boolean isKingstonPound) {
        this.isKingstonPound = isKingstonPound;
    }

    public PerformerData(Page page) {
        new PerformerData();
        this.setId(page.getId());
        this.setName(page.getName());
        this.setAbout(page.getAbout());
        this.setLink(page.getLink());
        if (page.getCover() != null) {
            this.setCoverImg(page.getCover().getSource());
        }
        if (page.getPicture() != null) {
            this.setProfileImg(page.getPicture().getData().getUrl());
        }
        this.setPhone(page.getPhone());
        this.setWebsite(page.getWebsite());
        this.setKingstonPound(page.isKingstonPound());
    }
}
