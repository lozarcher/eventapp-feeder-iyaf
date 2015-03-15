package com.loz.dao.responseVo;

import com.loz.dao.model.TweetData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class TweetResponse {
    private Date date;
    private Iterable<TweetData> data;
    private String next;

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

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

