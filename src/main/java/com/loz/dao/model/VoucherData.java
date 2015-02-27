package com.loz.dao.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VOUCHER")
public class VoucherData {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherData.class);

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URL")
    private String url;

    @Column(name = "POSITION")
    private int position;

    public VoucherData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}