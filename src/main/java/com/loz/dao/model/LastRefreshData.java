package com.loz.dao.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LAST_REFRESH")
public class LastRefreshData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "TABLE_NAME", unique = true)
    private String tableName;

    @Column(name = "LAST_REFRESH")
    private Date lastRefresh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefreshDate) {
        this.lastRefresh = lastRefreshDate;
    }
}
