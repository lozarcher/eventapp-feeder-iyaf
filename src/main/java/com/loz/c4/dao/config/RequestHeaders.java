package com.loz.c4.dao.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by loz on 05/10/2017.
 */
public class RequestHeaders {
    @JsonProperty("X-C4-API-Key")
    private String xC4APIKey;

    public String getxC4APIKey() {
        return xC4APIKey;
    }

    public void setxC4APIKey(String xC4APIKey) {
        this.xC4APIKey = xC4APIKey;
    }
}
