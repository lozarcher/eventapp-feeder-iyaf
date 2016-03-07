package com.loz.dao.responseVo;

import com.loz.dao.model.GalleryData;

import java.util.Date;

public class GalleryResponse {
    private Date date;
    private Iterable<GalleryData> data;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<GalleryData> getData() {
        return data;
    }

    public void setData(Iterable<GalleryData> data) {
        this.data = data;
    }
}
