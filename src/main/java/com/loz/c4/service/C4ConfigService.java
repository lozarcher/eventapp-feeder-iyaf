package com.loz.c4.service;

import com.loz.c4.controller.Installed;
import com.loz.c4.controller.PlatformConfig;
import com.loz.c4.exception.C4ConfigException;
import com.loz.feeder.dao.release.ReleaseResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.*;

@Service
public class C4ConfigService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(C4ConfigService.class);

    private static RestTemplate restTemplate;

    @Autowired
    private PropertiesMappingHTML5 propertiesMappingHTML5;

    @Autowired
    private PropertiesMappingRoku propertiesMappingRoku;

    protected RestOperations restTemplate() {
        if (restTemplate == null ) {
            restTemplate = new RestTemplate();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

            converter.setSupportedMediaTypes(
                    Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM}));

            restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        }
        return restTemplate;
    }

    private String getLegendTable() {
        return "<p><p><table border=1 cellpadding=3 cellspacing=0>"+
                "<tr><th>Value</th><th>Meaning</th></tr>"+
                "<tr><td>"+printEnabled()+"</td><td>Enabled</td></tr>"+
                "<tr><td>"+printDisabled()+"</td><td>Disabled</td></tr>"+
                "<tr><td>"+printNotPresent()+"</td><td>Not Present</td></tr>"+
                "</table>";
    }



    public String generatePropertiesPage() throws C4ConfigException {
        String output = "<html><head>"+
                "<style>"+
                "table { background-color: white; margin-left: auto; margin-right: auto; }"+
                "</style>"+
                "<script src=\"https://use.fontawesome.com/ce8b493cf2.js\"></script></head>"+
                "<body>";

        output += propertiesMappingHTML5.getPropertiesTable();

        output += propertiesMappingRoku.getPropertiesTable();

        output += getLegendTable();

        output += "</body></html>";

        return output;
    }

    protected void addPlatform(List<PlatformConfig> platformConfigs, List<Installed> platforms) {
        for (Installed platform : platforms) {
            PlatformConfig platformConfig = new PlatformConfig();
            platformConfig.setPlatformName(platform.getTitle());
            platformConfig.setConfigUrl(platform.getConfigUrl());
            platformConfig.setKey(platform.getKey());
            platformConfig.setVersionUrl(platform.getVersionUrl());
            platformConfigs.add(platformConfig);
        }
    }


    public boolean isUrlValid(String uri) {
        final URL url;
        try {
            url = new URL(uri);
        } catch (Exception e1) {
            return false;
        }
        return url.getProtocol().contains("http");
    }

    protected String displayValue(Boolean value, boolean trueMeansEnabled) {
        if (value == null) return printNotPresent();
        if (value == trueMeansEnabled) {
            return printEnabled();
        } else {
            return printDisabled();
        }
    }

    protected String printEnabled() {
        return "<i class=\"fa fa-check-circle\" style=\"color: green\" aria-hidden=\"true\"></i>";
        //return "yes";
    }

    protected String printDisabled() {
        return "<i class=\"fa fa-times-circle\" style=\"color: grey\" aria-hidden=\"true\"></i>";
        //return "";
    }

    protected String printNotPresent() {
        return "";
        //return "<i class=\"fa fa-question\" style=\"color: grey\" aria-hidden=\"true\"></i>";

    }

    protected String getVersion(String versionUrl) {
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

