package com.loz.c4.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Installed {
    SAMSUNG_INT("Samsung INT", "samsung.key", "https://samsung.int.channel4.com/properties/6.16/all4-samsung-properties-prod.json", "https://samsung.int.channel4.com/properties/releasenumber.xml"),
    SAMSUNG_TEST("Samsung TEST", "samsung.key", "https://samsung.test.channel4.com/properties/6.16/all4-samsung-properties-prod.json", "https://samsung.test.channel4.com/properties/releasenumber.xml"),
    SAMSUNG_STAGE("Samsung STAGE", "samsung.key", "https://samsung.stage.channel4.com/properties/6.16/all4-samsung-properties-prod.json", "https://samsung.stage.channel4.com/properties/releasenumber.xml"),
    SAMSUNG_PROD("Samsung PROD", "samsung.key", "https://samsung.channel4.com/properties/6.16/all4-samsung-properties-prod.json", "https://samsung.channel4.com/properties/releasenumber.xml");

    private String title;
    private String keyProperty;
    private String configUrl;
    private String versionUrl;

    Installed(String title, String keyProperty, String configUrl, String versionUrl) {
        this.title = title;
        this.keyProperty = keyProperty;
        this.configUrl = configUrl;
        this.versionUrl = versionUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:application.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
        }
        return properties.getProperty(this.keyProperty);
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public String getConfigUrl() {
        return configUrl;
    }
}
