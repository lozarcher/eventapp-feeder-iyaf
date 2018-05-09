package com.loz.feeder.dao.responseVo;

import com.loz.feeder.dao.model.CategoryData;
import com.loz.feeder.dao.model.EventData;

import java.util.Date;

/**
 * Created by loz on 14/02/15.
 */
public class EventCategoryResponse {
    private Date date;
    private Iterable<EventData> events;
    private Iterable<CategoryData> categories;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Iterable<EventData> getEvents() {
        return events;
    }

    public void setEvents(Iterable<EventData> events) {
        this.events = events;
    }

    public Iterable<CategoryData> getCategories() {
        return categories;
    }

    public void setCategories(Iterable<CategoryData> categories) {
        this.categories = categories;
    }
}
