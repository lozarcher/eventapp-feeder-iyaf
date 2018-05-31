package com.loz.c4.service;

import com.loz.c4.controller.Installed;
import com.loz.c4.controller.InstalledPlatformsHTML5;
import com.loz.c4.controller.Platform;
import com.loz.c4.controller.PlatformConfig;
import com.loz.c4.dao.config.ConfigResponse;
import com.loz.c4.dao.properties.html5.PropertiesResponse;
import com.loz.c4.exception.C4ConfigException;
import com.loz.feeder.dao.release.ReleaseResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PropertiesMappingHTML5 implements C4PropertiesMapping {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PropertiesMappingHTML5.class);

    @Autowired
    public InstalledPlatformsHTML5 installedPlatforms;

    public String getPropertiesTable() throws C4ConfigException {
        String output = "<table border=1 cellpadding=3 cellspacing=0><tr>"+
                "<td><strong>Platform</strong></td>"+
                "<td><strong>Version</strong></td><td><strong>Subtitles</strong></td>"+
                "<td><strong>Sumo Metrics</strong></td><td><strong>Sumo Logging</strong></td><td><strong>MVT</strong></td><td><strong>Innovid</strong></td><td><strong>Autoplay Limit</strong></td>"
                +"<td><strong>User Alerts</strong></td><td><strong>Kantar</strong></td><td><strong>Credit Squeeze</strong></td><td><strong>Animation</strong></td>"+
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

        for (Installed platform : installedPlatforms.getPlatforms()) {
            PlatformConfig platformConfig = new PlatformConfig();
            platformConfig.setPlatformName(platform.getTitle());
            platformConfig.setConfigUrl(platform.getConfigUrl());
            platformConfig.setKey(platform.getKey());
            platformConfig.setVersionUrl(platform.getVersionUrl());
            platformConfigs.add(platformConfig);
        }


        for (PlatformConfig platformConfig : platformConfigs) {

            ResponseEntity<PropertiesResponse> response = null;

            HttpHeaders headers = new HttpHeaders();
            //headers.set("X-C4-API-Key", configResponse.getApi().getRequestParameters().getApikey());
            headers.set("X-C4-API-Key", platformConfig.getKey());
            HttpEntity entity = new HttpEntity(headers);

            boolean success = false;
            if (C4ConfigService.isUrlValid(platformConfig.getConfigUrl())) {
                try {
                    response = C4ConfigService.restTemplate().exchange(URLDecoder.decode(platformConfig.getConfigUrl()), HttpMethod.GET, entity, PropertiesResponse.class);
                    output += "<tr>" +
                            "<td><strong>" + platformConfig.getPlatformName() + "</strong></td>" +
                            "<td>" + getVersion(platformConfig.getVersionUrl()) + "</td>" +
                            "<td>" + getSubtitles(response.getBody()) + "</td>" +
                            "<td>" + getMetrics(response.getBody()) + "</td>" +
                            "<td>" + getLogging(response.getBody()) + "</td>" +
                            "<td>" + getMvt(response.getBody()) + "</td>" +
                            "<td>" + getInnovid(response.getBody()) + "</td>" +
                            "<td>" + getAutoplayLimit(response.getBody()) + "</td>" +
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
                //response = restTemplate.getForEntity(URLDecoder.decode(configUrl, "UTF-8"), String.class);
            }
            if (!success) {
                output += "<tr><td><strong>" + platformConfig.getPlatformName() + "</strong></td><td colspan=\"13\">There is an error reading the config from " + platformConfig.getPlatformName() + "</td></tr>";
            }
        }
        output += "</table>";
        return output;
    }

    private ConfigResponse getConfigResponse(String configUrl) throws C4ConfigException {
        ResponseEntity<ConfigResponse> response = null;
        try {
            response = C4ConfigService.restTemplate().getForEntity(URLDecoder.decode(configUrl, "UTF-8"), ConfigResponse.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }
        return response.getBody();
    }

    private String getSubtitles(PropertiesResponse properties) {
        Boolean subtitles;
        try {
            subtitles = properties.getConfig().getDisabledFeatures().getSubtitles();
            return C4ConfigService.displayValue(subtitles, false);
        }
        catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }


    private String getUserAlerts(PropertiesResponse properties) {
        Boolean userAlerts;
        try {
            userAlerts = properties.getConfig().getDisabledFeatures().getUserAlerts();
            return C4ConfigService.displayValue(userAlerts, false);
        }
        catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }


    private String getKantar(PropertiesResponse properties) {
        Boolean kantar;
        try {
            kantar = properties.getConfig().getDisabledFeatures().getKantar();
            return C4ConfigService.displayValue(kantar, false);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getMetrics(PropertiesResponse properties) {
        Boolean metrics;
        try {
            metrics = properties.getConfig().getDisabledFeatures().getMetrics();
            return C4ConfigService.displayValue(metrics, false);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getMvt(PropertiesResponse properties) {
        Boolean mvt;
        try {
            mvt = properties.getConfig().getDisabledFeatures().getMvt();
            return C4ConfigService.displayValue(mvt, false);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getInnovid(PropertiesResponse properties) {
        Boolean innovid;
        try {
            innovid = properties.getConfig().getDisabledFeatures().getMvt();
            return C4ConfigService.displayValue(innovid, false);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getAutoplayLimit(PropertiesResponse properties) {
        Boolean autoplayLimit;
        try {
            autoplayLimit = properties.getConfig().getDisabledFeatures().getAutoplayLimit();
            if (autoplayLimit) {
                return C4ConfigService.printDisabled();
            } else {
                return properties.getConfig().getClientProperties().getAutoplayLimit().toString();
            }
        }
        catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getCreditSqueeze(PropertiesResponse properties) {
        Boolean creditSqueeze;
        try {
            creditSqueeze = properties.getConfig().getCreditSqueeze();
            return C4ConfigService.displayValue(creditSqueeze, true);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getAnimation(PropertiesResponse properties) {
        Boolean animation;
        try {
            animation = properties.getConfig().getAnimation();
            return C4ConfigService.displayValue(animation, true);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getConviva(PropertiesResponse properties) {
        Boolean conviva;
        try {
            conviva = properties.getConfig().getClientProperties().getConviva().getEnabled();
            return C4ConfigService.displayValue(conviva, true);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getLogging(PropertiesResponse properties) {
        Boolean logging;
        try {
            logging = properties.getConfig().getClientProperties().getSumoLogic().getEnabled();
            return C4ConfigService.displayValue(logging, true);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getPromoAlert(PropertiesResponse properties) {
        Boolean promoAlert;
        try {
            promoAlert = properties.getConfig().getClientProperties().getPromoAlert().isDisplay();
            return C4ConfigService.displayValue(promoAlert, true);
        } catch (NullPointerException e) {
            return C4ConfigService.printNotPresent();
        }
    }

    private String getVersion(String versionUrl) {
        RestTemplate rest = new RestTemplate();
        VersionXmlMessageConverter converter = new VersionXmlMessageConverter();
        rest.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        ReleaseResponse release;
        try {
            release = rest.getForObject(versionUrl,
                    ReleaseResponse.class);
        } catch (RuntimeException e) {
            return "-";
        }
        if (release != null) {
            return release.getVersion();
        } else {
            return "-";
        }
    }

    private class VersionXmlMessageConverter extends MappingJackson2XmlHttpMessageConverter {
        private VersionXmlMessageConverter() {
            List<MediaType> types = Arrays.asList(
                    new MediaType("application", "json", DEFAULT_CHARSET),
                    new MediaType("application", "xml", DEFAULT_CHARSET)
            );
            super.setSupportedMediaTypes(types);
        }
    }
}
