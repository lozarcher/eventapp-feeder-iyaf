package com.loz.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by loz on 23/02/15.
 */
@Entity
@Table(name = "PERFORMER_FEED")
public class PerformerFeedData implements Serializable{
    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    public PerformerFeedData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
