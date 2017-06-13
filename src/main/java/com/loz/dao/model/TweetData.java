package com.loz.dao.model;

import com.loz.dao.feed.twitter.Entities;
import com.loz.dao.feed.twitter.Media;
import com.loz.dao.feed.twitter.Status;
import com.loz.dao.feed.twitter.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "TWEET")
public class TweetData {

    private static final Logger LOGGER = LoggerFactory.getLogger(TweetData.class);

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "PROFILE_PIC")
    private String profilePic;

    public TweetData() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getProfilePic() {
        return profilePic.replaceFirst("http://", "https://");
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public TweetData(Status status) {
        new TweetData();
        this.setId(Long.parseLong(status.getId_str()));
        User user = status.getUser();
        if (user != null) {
            this.setName(user.getName());
            this.setScreenName(user.getScreen_name());
            this.setProfilePic(user.getProfile_image_url());
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date result = df.parse(status.getCreated_at());
                this.setCreatedDate(result);
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("Could not parse Tweet date {}", status.getCreated_at());
            }
        }
        String text = status.getText();
        Entities entities = status.getEntities();
        text = text.replaceAll("http[^\\s]+", "");
        if (entities != null) {
            if (entities.getMedia() != null) {
                for (Media media : entities.getMedia()) {
                    text += " " + media.getMedia_url();
                }
            }
        }
        this.setText(text);
    }
}