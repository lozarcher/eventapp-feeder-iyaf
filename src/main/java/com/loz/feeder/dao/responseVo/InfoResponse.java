package com.loz.feeder.dao.responseVo;

import com.loz.feeder.dao.model.InfoData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class InfoResponse {
    private Date date;
    private Iterable<InfoData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<InfoData> getData() {
        return data;
    }

    public void setData(Iterable<InfoData> data) {
        this.data = data;
    }
}
