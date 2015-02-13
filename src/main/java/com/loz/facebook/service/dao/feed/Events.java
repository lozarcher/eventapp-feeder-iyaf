package com.loz.facebook.service.dao.feed;

import java.util.List;

public class Events {

    private List<Event> data;
    private Paging paging;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
