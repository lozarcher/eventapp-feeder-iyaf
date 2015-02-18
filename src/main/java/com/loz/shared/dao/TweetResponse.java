package com.loz.shared.dao;

import com.loz.facebook.service.dao.model.EventData;
import com.loz.twitter.service.dao.domain.TweetData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class TweetResponse {
    private Date date;
    private Iterable<TweetData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<TweetData> getData() {
        return data;
    }

    public void setData(Iterable<TweetData> data) {
        this.data = data;
    }
}
