package com.loz.c4.dao.config;

/**
 * Created by loz on 05/10/2017.
 */
public class ConfigResponse {
    private Config config;
    private Api api;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}
