package com.loz.c4.dao.properties.html5;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by loz on 05/10/2017.
 */
public class Config {
    private DisabledFeatures disabledFeatures;
    private Boolean creditSqueeze;
    private Boolean animation;

    @JsonProperty("clientproperties")
    private ClientProperties clientProperties;

    public DisabledFeatures getDisabledFeatures() {
        return disabledFeatures;
    }

    public void setDisabledFeatures(DisabledFeatures disabledFeatures) {
        this.disabledFeatures = disabledFeatures;
    }

    public Boolean getCreditSqueeze() {
        return creditSqueeze;
    }

    public void setCreditSqueeze(Boolean creditSqueeze) {
        this.creditSqueeze = creditSqueeze;
    }

    public Boolean getAnimation() {
        return animation;
    }

    public void setAnimation(Boolean animation) {
        this.animation = animation;
    }

    public ClientProperties getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }
}
