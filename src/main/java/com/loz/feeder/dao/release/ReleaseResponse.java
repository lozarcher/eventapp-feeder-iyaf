package com.loz.feeder.dao.release;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by loz on 06/10/2017.
 */
@XmlRootElement(name = "release")
public class ReleaseResponse {
    @XmlElement
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
