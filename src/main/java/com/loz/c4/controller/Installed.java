package com.loz.c4.controller;

public class Installed {

    private String title;
    private String key;
    private String configVersion;
    private String configUrl;
    private String versionUrl;

    public Installed(String title, String versionUrl, String configUrl, String key, String configVersion) {
        this.setTitle(title);
        this.setKey(key);
        this.setConfigVersion(configVersion);
        this.setConfigUrl(configUrl);
        this.setVersionUrl(versionUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }
}
