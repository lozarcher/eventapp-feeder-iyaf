package com.loz.shared.dao;

import com.loz.facebook.service.dao.model.VenueData;
import com.loz.twitter.service.dao.domain.TweetData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class VenueResponse {
    private Date date;
    private Iterable<VenueData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<VenueData> getData() {
        return data;
    }

    public void setData(Iterable<VenueData> data) {
        this.data = data;
    }
}
