package com.loz.twitter.service.dao.feed;

import java.util.List;

/**
 * Created by loz on 05/02/15.
 */
public class TwitterResponse {
    private List<Status> statuses;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
