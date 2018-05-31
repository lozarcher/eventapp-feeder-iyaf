package com.loz.c4.controller;

/**
 * Created by loz on 05/10/2017.
 */
public enum Platform {
    AMAZON_INT ("Amazon INT", "https://amazonfire-p06.int.channel4.com/amazonfire/config/amazonfire.json","https://amazonfire-p06.int.channel4.com/properties/releasenumber.xml"),
    AMAZON_TEST ("Amazon TEST", "https://amazonfire-p06.test.channel4.com/amazonfire/config/amazonfire.json","https://amazonfire-p06.test.channel4.com/properties/releasenumber.xml"),
    AMAZON_STAGE ("Amazon STAGE", "https://amazonfire-p06.stage.channel4.com/amazonfire/config/amazonfire.json","https://amazonfire-p06.stage.channel4.com/properties/releasenumber.xml"),
    AMAZON_PROD ("Amazon PROD", "https://amazonfire-p06.channel4.com/amazonfire/config/amazonfire.json","https://amazonfire-p06.channel4.com/properties/releasenumber.xml"),
    FREEVIEW_INT ("Freeview INT", "https://fvc-p06.int.channel4.com/fvc-webapp/config/freeview.json", "https://fvc-p06.int.channel4.com/properties/releasenumber.xml"),
    FREEVIEW_TEST ("Freeview TEST", "https://fvc-p06.test.channel4.com/fvc-webapp/config/freeview.json", "https://fvc-p06.test.channel4.com/properties/releasenumber.xml"),
    FREEVIEW_STAGE ("Freeview STAGE", "https://fvc-p06.stage.channel4.com/fvc-webapp/config/freeview.json", "https://fvc-p06.stage.channel4.com/properties/releasenumber.xml"),
    FREEVIEW_PROD ("Freeview PROD", "https://fvc-p06.channel4.com/fvc-webapp/config/freeview.json", "https://fvc-p06.channel4.com/properties/releasenumber.xml"),
//    FREESAT_INT("Freesat INT", "https://freesat.int.channel4.com/4od-freetime/config/freesat.json", "https://freesat.int.channel4.com/properties/releasenumber.xml"),
//    FREESAT_TEST("Freesat TEST", "https://freesat.test.channel4.com/4od-freetime/config/freesat.json", "https://freesat.test.channel4.com/properties/releasenumber.xml"),
//    FREESAT_STAGE("Freesat STAGE", "https://freesat.stage.channel4.com/4od-freetime/config/freesat.json", "https://freesat.stage.channel4.com/properties/releasenumber.xml"),
//    FREESAT_PROD("Freesat PROD", "https://freesat.channel4.com/4od-freetime/config/freesat.json", "https://freesat.channel4.com/properties/releasenumber.xml"),
    PS3_INT("PS3 INT", "https://ps-p06.int.channel4.com/app/v2/ps3-webmaf/config/ps3.json", "https://ps-p06.int.channel4.com/properties/releasenumber.xml"),
    PS3_TEST("PS3 TEST", "https://ps-p06.test.channel4.com/app/v2/ps3-webmaf/config/ps3.json", "https://ps-p06.test.channel4.com/properties/releasenumber.xml"),
    PS3_STAGE("PS3 STAGE", "https://ps-p06.stage.channel4.com/app/v2/ps3-webmaf/config/ps3.json", "https://ps-p06.stage.channel4.com/properties/releasenumber.xml"),
    PS3_PROD("PS3 PROD", "https://ps-p06.channel4.com/app/v2/ps3-webmaf/config/ps3.json", "https://ps-p06.channel4.com/properties/releasenumber.xml"),
    PS4_INT("PS4 INT", "https://ps-p06.int.channel4.com/app/v2/ps3-webmaf/config/ps4.json", "https://ps-p06.int.channel4.com/properties/releasenumber.xml"),
    PS4_TEST("PS4 TEST", "https://ps-p06.test.channel4.com/app/v2/ps3-webmaf/config/ps4.json", "https://ps-p06.test.channel4.com/properties/releasenumber.xml"),
    PS4_STAGE("PS4 STAGE", "https://ps-p06.stage.channel4.com/app/v2/ps3-webmaf/config/ps4.json", "https://ps-p06.stage.channel4.com/properties/releasenumber.xml"),
    PS4_PROD("PS4 PROD", "https://ps-p06.channel4.com/app/v2/ps3-webmaf/config/ps4.json", "https://ps-p06.channel4.com/properties/releasenumber.xml"),
    YOUVIEW_INT("YouView INT", "https://yvweb.int.channel4.com/yvwebapp/config/youview.json", "https://yvweb.int.channel4.com/properties/releasenumber.xml"),
    YOUVIEW_TEST("YouView TEST", "https://yvweb.test.channel4.com/yvwebapp/config/youview.json", "https://yvweb.test.channel4.com/properties/releasenumber.xml"),
    YOUVIEW_STAGE("YouView STAGE", "https://yvweb.stage.channel4.com/yvwebapp/config/youview.json", "https://yvweb.stage.channel4.com/properties/releasenumber.xml"),
    YOUVIEW_PROD("YouView PROD", "https://yvweb.channel4.com/yvwebapp/config/youview.json", "https://yvweb.channel4.com/properties/releasenumber.xml");


    private String title;
    private String configUrl;
    private String versionUrl;

    Platform(String title, String configUrl, String versionUrl) {
        this.title = title;
        this.configUrl = configUrl;
        this.versionUrl = versionUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

}
