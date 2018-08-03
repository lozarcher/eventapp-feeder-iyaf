package com.loz.c4.service;

import com.loz.c4.controller.InstalledPlatformsHTML5;
import com.loz.c4.controller.Platform;
import com.loz.c4.controller.PlatformConfig;
import com.loz.c4.dao.config.ConfigResponse;
import com.loz.c4.dao.properties.html5.PropertiesResponse;
import com.loz.c4.exception.C4ConfigException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertiesMappingHTML5 implements C4PropertiesMapping {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PropertiesMappingHTML5.class);

    @Autowired
    public InstalledPlatformsHTML5 installedPlatforms;

    @Autowired
    public C4ConfigService configService;

    public String getPropertiesTable() throws C4ConfigException {
        String output = "<table border=1 cellpadding=3 cellspacing=0><tr>"+
                "<td><strong>Platform</strong></td>"+
                "<td><strong>Version</strong></td><td><strong>Subtitles</strong></td>"+
                "<td><strong>Sumo Metrics</strong></td><td><strong>Sumo Logging</strong></td><td><strong>MVT</strong></td><td><strong>Innovid</strong></td><td><strong>Autoplay Limit</strong></td>"+
                "<td><strong>Ad Pause</strong></td><td><strong>User Alerts</strong></td><td><strong>Kantar</strong></td><td><strong>Credit Squeeze</strong></td><td><strong>Animation</strong></td>"+
                "<td><strong>Conviva</strong></td>"+
                "<td><strong>Add to My List</strong></td>"+
                "</tr>";

        List<PlatformConfig> platformConfigs = new ArrayList<>();

        for (Platform platform : Platform.values()) {
            ConfigResponse configResponse = getConfigResponse(platform.getConfigUrl());
            String configUrl = configResponse.getConfig().getUrl() +
                    configResponse.getConfig().getVersion() +
                    ".json";
            String key = configResponse.getApi().getRequestHeaders().getxC4APIKey();

            PlatformConfig platformConfig = new PlatformConfig();
            platformConfig.setPlatformName(platform.getTitle());
            platformConfig.setKey(key);
            platformConfig.setConfigUrl(configUrl);
            platformConfig.setVersionUrl(platform.getVersionUrl());
            platformConfigs.add(platformConfig);
        }

        configService.addPlatform(platformConfigs, installedPlatforms.getPlatforms());


        for (PlatformConfig platformConfig : platformConfigs) {

            ResponseEntity<PropertiesResponse> response = null;

            HttpHeaders headers = new HttpHeaders();
            //headers.set("X-C4-API-Key", configResponse.getApi().getRequestParameters().getApikey());
            headers.set("X-C4-API-Key", platformConfig.getKey());
            HttpEntity entity = new HttpEntity(headers);

            boolean success = false;
            if (configService.isUrlValid(platformConfig.getConfigUrl())) {
                try {
                    response = configService.restTemplate().exchange(URLDecoder.decode(platformConfig.getConfigUrl()), HttpMethod.GET, entity, PropertiesResponse.class);
                    output += "<tr>" +
                            "<td><strong>" + platformConfig.getPlatformName() + "</strong></td>" +
                            "<td>" + configService.getVersion(platformConfig.getVersionUrl()) + "</td>" +
                            "<td>" + getSubtitles(response.getBody()) + "</td>" +
                            "<td>" + getMetrics(response.getBody()) + "</td>" +
                            "<td>" + getLogging(response.getBody()) + "</td>" +
                            "<td>" + getMvt(response.getBody()) + "</td>" +
                            "<td>" + getInnovid(response.getBody()) + "</td>" +
                            "<td>" + getAutoplayLimit(response.getBody()) + "</td>" +
                            "<td>" + getAdPause(response.getBody()) + "</td>" +
                            "<td>" + getUserAlerts(response.getBody()) + "</td>" +
                            "<td>" + getKantar(response.getBody()) + "</td>" +
                            "<td>" + getCreditSqueeze(response.getBody()) + "</td>" +
                            "<td>" + getAnimation(response.getBody()) + "</td>" +
                            "<td>" + getConviva(response.getBody()) + "</td>" +
                            "<td>" + getPromoAlert(response.getBody()) + "</td>" +
                            "</tr>";
                    success = true;
                    LOGGER.info("Processed "+platformConfig.getPlatformName());
                } catch (Exception e) {
                    LOGGER.error(e.getLocalizedMessage()+" for "+platformConfig.getPlatformName());
                }
            }
            if (!success) {
                output += "<tr><td><strong>"
                        + platformConfig.getPlatformName() +
                        "</strong></td><td colspan=\"13\">There is an error reading the config from "
                        + platformConfig.getPlatformName()
                        + "</td></tr>";
            }
        }
        output += "</table>";
        return output;
    }

    private ConfigResponse getConfigResponse(String configUrl) throws C4ConfigException {
        ResponseEntity<ConfigResponse> response = null;
        try {
            response = configService.restTemplate().getForEntity(URLDecoder.decode(configUrl, "UTF-8"), ConfigResponse.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }
        return response.getBody();
    }

    private String getSubtitles(PropertiesResponse properties) {
        Boolean subtitles;
        try {
            subtitles = properties.getConfig().getDisabledFeatures().getSubtitles();
            return configService.displayValue(subtitles, false);
        }
        catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }


    private String getUserAlerts(PropertiesResponse properties) {
        Boolean userAlerts;
        try {
            userAlerts = properties.getConfig().getDisabledFeatures().getUserAlerts();
            return configService.displayValue(userAlerts, false);
        }
        catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }


    private String getKantar(PropertiesResponse properties) {
        Boolean kantar;
        try {
            kantar = properties.getConfig().getDisabledFeatures().getKantar();
            return configService.displayValue(kantar, false);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getMetrics(PropertiesResponse properties) {
        Boolean metrics;
        try {
            metrics = properties.getConfig().getDisabledFeatures().getMetrics();
            return configService.displayValue(metrics, false);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getMvt(PropertiesResponse properties) {
        Boolean mvt;
        try {
            mvt = properties.getConfig().getDisabledFeatures().getMvt();
            return configService.displayValue(mvt, false);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getInnovid(PropertiesResponse properties) {
        Boolean innovid;
        try {
            innovid = properties.getConfig().getDisabledFeatures().getInnovid();
            return configService.displayValue(innovid, false);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getAutoplayLimit(PropertiesResponse properties) {
        Boolean autoplayLimit;
        try {
            autoplayLimit = properties.getConfig().getDisabledFeatures().getAutoplayLimit();
            if (autoplayLimit) {
                return configService.printDisabled();
            } else {
                return properties.getConfig().getClientProperties().getAutoplayLimit().toString();
            }
        }
        catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getAdPause(PropertiesResponse properties) {
        Boolean adPause;
        try {
            adPause = properties.getConfig().getDisabledFeatures().getPauseAd();
            if (adPause) {
                return configService.printDisabled();
            } else {
                return configService.printEnabled();
            }
        }
        catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getCreditSqueeze(PropertiesResponse properties) {
        Boolean creditSqueeze;
        try {
            creditSqueeze = properties.getConfig().getCreditSqueeze();
            return configService.displayValue(creditSqueeze, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getAnimation(PropertiesResponse properties) {
        Boolean animation;
        try {
            animation = properties.getConfig().getAnimation();
            return configService.displayValue(animation, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getConviva(PropertiesResponse properties) {
        Boolean conviva;
        try {
            conviva = properties.getConfig().getClientProperties().getConviva().getEnabled();
            return configService.displayValue(conviva, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getLogging(PropertiesResponse properties) {
        Boolean logging;
        try {
            logging = properties.getConfig().getClientProperties().getSumoLogic().getEnabled();
            return configService.displayValue(logging, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }

    private String getPromoAlert(PropertiesResponse properties) {
        Boolean promoAlert;
        try {
            promoAlert = properties.getConfig().getClientProperties().getPromoAlert().isDisplay();
            return configService.displayValue(promoAlert, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }




}
