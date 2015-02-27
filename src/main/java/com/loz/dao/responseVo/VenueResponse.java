package com.loz.dao.responseVo;

import com.loz.dao.model.VenueData;

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
