package com.loz.feeder.dao.responseVo;

import com.loz.feeder.dao.model.PerformerData;

import java.util.Date;

/**
 * Created by larcher on 13/03/2015.
 */
public class PageResponse {
    private Date date;
    private Iterable<PerformerData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<PerformerData> getData() {
        return data;
    }

    public void setData(Iterable<PerformerData> data) {
        this.data = data;
    }
}
