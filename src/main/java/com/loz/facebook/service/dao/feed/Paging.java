package com.loz.facebook.service.dao.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by larcher on 04/02/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paging {
    private String next;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
