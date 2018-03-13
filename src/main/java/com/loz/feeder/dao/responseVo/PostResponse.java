package com.loz.feeder.dao.responseVo;

import com.loz.feeder.dao.model.PostData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class PostResponse {
    private Date date;
    private Iterable<PostData> data;
    private String next;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<PostData> getData() {
        return data;
    }

    public void setData(Iterable<PostData> data) {
        this.data = data;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}

