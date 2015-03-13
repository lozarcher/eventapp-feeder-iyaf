package com.loz.dao.responseVo;

import com.loz.dao.model.TraderData;
import com.loz.dao.model.TweetData;

import java.util.Date;

/**
 * Created by larcher on 13/03/2015.
 */
public class PageResponse {
    private Date date;
    private Iterable<TraderData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<TraderData> getData() {
        return data;
    }

    public void setData(Iterable<TraderData> data) {
        this.data = data;
    }
}
