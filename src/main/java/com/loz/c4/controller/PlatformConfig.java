package com.loz.c4.controller;

public class PlatformConfig {
    private String platformName;
    private String key;
    private String configUrl;
    private String versionUrl;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }
}
