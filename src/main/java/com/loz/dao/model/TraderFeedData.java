package com.loz.dao.model;

import com.loz.dao.feed.facebook.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by loz on 23/02/15.
 */
@Entity
@Table(name = "TRADER_FEED")
public class TraderFeedData implements Serializable{
    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "KPOUND")
    private boolean isKingstonPound;

    public TraderFeedData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isKingstonPound() {
        return isKingstonPound;
    }

    public void setKingstonPound(boolean isKingstonPound) {
        this.isKingstonPound = isKingstonPound;
    }
}
