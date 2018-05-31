package com.loz.c4.dao.properties.roku;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loz.c4.dao.properties.html5.ClientProperties;
import com.loz.c4.dao.properties.html5.DisabledFeatures;

/**
 * Created by loz on 05/10/2017.
 */
public class Config {
    private Conviva conviva;

    public Conviva getConviva() {
        return conviva;
    }

    public void setConviva(Conviva conviva) {
        this.conviva = conviva;
    }
}
