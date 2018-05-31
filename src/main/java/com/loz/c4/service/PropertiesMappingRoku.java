package com.loz.c4.service;

import com.loz.c4.controller.Installed;
import com.loz.c4.controller.InstalledPlatformsRoku;
import com.loz.c4.controller.PlatformConfig;
import com.loz.c4.dao.properties.roku.PropertiesResponse;
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
public class PropertiesMappingRoku implements C4PropertiesMapping {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PropertiesMappingRoku.class);

    @Autowired
    public InstalledPlatformsRoku installedPlatforms;

    @Autowired
    public C4ConfigService configService;

    public String getPropertiesTable() throws C4ConfigException {
        String output = "<p><p><table border=1 cellpadding=3 cellspacing=0><tr>"+
                "<td><strong>Platform</strong></td>"+
                "<td><strong>Version</strong></td>"+
                "<td><strong>Conviva</strong></td>"+
                "</tr>";

        List<PlatformConfig> platformConfigs = new ArrayList<>();

        configService.addPlatform(platformConfigs, installedPlatforms.getPlatforms());

        for (PlatformConfig platformConfig : platformConfigs) {

            ResponseEntity<PropertiesResponse> response = null;

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-C4-API-Key", platformConfig.getKey());
            HttpEntity entity = new HttpEntity(headers);

            boolean success = false;
            if (configService.isUrlValid(platformConfig.getConfigUrl())) {
                try {
                    response = configService.restTemplate().exchange(URLDecoder.decode(platformConfig.getConfigUrl()), HttpMethod.GET, entity, PropertiesResponse.class);
                    output += "<tr>" +
                            "<td><strong>" + platformConfig.getPlatformName() + "</strong></td>" +
                            "<td>" + configService.getVersion(platformConfig.getVersionUrl()) + "</td>" +
                            "<td>" + getConviva(response.getBody()) + "</td>" +
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

    private String getConviva(PropertiesResponse properties) {
        Boolean conviva;
        try {
            conviva = properties.getConfig().getConviva().getEnabled();
            return configService.displayValue(conviva, true);
        } catch (NullPointerException e) {
            return configService.printNotPresent();
        }
    }
}
