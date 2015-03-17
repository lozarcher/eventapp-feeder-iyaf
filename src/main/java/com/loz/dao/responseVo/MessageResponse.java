package com.loz.dao.responseVo;

import com.loz.dao.model.MessageData;
import com.loz.dao.model.TweetData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class MessageResponse {
    private Date date;
    private Iterable<MessageData> data;
    private String next;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<MessageData> getData() {
        return data;
    }

    public void setData(Iterable<MessageData> data) {
        this.data = data;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

