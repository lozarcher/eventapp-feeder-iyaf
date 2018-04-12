package com.loz.feeder.dao.feed.facebook.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by loz on 05/02/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cover {
    private String source;
    private Integer offset_x;
    private Integer offset_y;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(Integer offset_x) {
        this.offset_x = offset_x;
    }

    public Integer getOffset_y() {
        return offset_y;
    }

    public void setOffset_y(Integer offset_y) {
        this.offset_y = offset_y;
    }
}
