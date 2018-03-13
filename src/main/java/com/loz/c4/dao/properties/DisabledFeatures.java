package com.loz.c4.dao.properties;

/**
 * Created by loz on 05/10/2017.
 */
public class DisabledFeatures {
    private Boolean subtitles;
    private Boolean userAlerts;
    private Boolean kantar;

    public Boolean getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Boolean subtitles) {
        this.subtitles = subtitles;
    }

    public Boolean getUserAlerts() {
        return userAlerts;
    }

    public void setUserAlerts(Boolean userAlerts) {
        this.userAlerts = userAlerts;
    }

    public Boolean getKantar() {
        return kantar;
    }

    public void setKantar(Boolean kantar) {
        this.kantar = kantar;
    }
}
