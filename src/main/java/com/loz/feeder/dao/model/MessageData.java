package com.loz.feeder.dao.model;

import com.loz.feeder.dao.feed.twitter.Status;
import com.loz.feeder.dao.feed.twitter.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "MESSAGE")
public class MessageData implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageData.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "PROFILE_PIC")
    private String profilePic;

    public MessageData() {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public MessageData(Status status) {
        new MessageData();
        this.setId(Long.parseLong(status.getId_str()));
        User user = status.getUser();
        if (user != null) {
            this.setName(user.getName());
            this.setProfilePic(user.getProfile_image_url());
            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
            try {
                Date result =  df.parse(status.getCreated_at());
                this.setCreatedDate(result);
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("Could not parse Tweet date {}", status.getCreated_at());
            }
        }
        this.setText(status.getText());
    }
}