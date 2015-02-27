package com.loz.dao.feed.facebook;

import java.util.List;

public class EventResponse {

    private List<Event> data;
    private Paging paging;

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> events) {
        this.data = events;
    }

    public void addData(List<Event> moreEvents) {
        if (this.data == null) {
            this.data = moreEvents;
        } else {
            this.data.addAll(moreEvents);
        }
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
