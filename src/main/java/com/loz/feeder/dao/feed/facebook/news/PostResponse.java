package com.loz.feeder.dao.feed.facebook.news;

import com.loz.feeder.dao.feed.facebook.common.Paging;

import java.util.List;

/**
 * Created by larcher on 27/05/2015.
 */
public class PostResponse {
    private List<Post> data;
    private Paging paging;

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> posts) {
        this.data = posts;
    }

    public void addData(List<Post> morePosts) {
        if (this.data == null) {
            this.data = morePosts;
        } else {
            this.data.addAll(morePosts);
        }
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
