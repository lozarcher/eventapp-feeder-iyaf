package com.loz.c4.service;

import com.loz.c4.controller.Platform;
import com.loz.c4.dao.config.ConfigResponse;
import com.loz.c4.dao.properties.PropertiesResponse;
import com.loz.feeder.dao.release.ReleaseResponse;
import com.loz.c4.exception.C4ConfigException;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Arrays;
import java.util.List;

@Service
public class C4ConfigService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(C4ConfigService.class);

    public RestOperations restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(
                Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM}));

        restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
        return restTemplate;
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

    private ConfigResponse getConfigResponse(String configUrl) throws C4ConfigException {
        ResponseEntity<ConfigResponse> response = null;
        try {
            response = restTemplate().getForEntity(URLDecoder.decode(configUrl, "UTF-8"), ConfigResponse.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
        }
        return response.getBody();
    }

    public String getProperties() throws C4ConfigException {
        String output = "<html><head><script src=\"https://use.fontawesome.com/ce8b493cf2.js\"></script></head>"+
                "<body><table border=1><tr>"+
                "<td>Platform</td>"+
                "<td>Version</td><td>Subtitles</td><td>User Alerts</td><td>Kantar</td><td>Credit Squeeze</td><td>Animation</td>"+
                "<td>Conviva</td>"+
                "</tr>";

        for (Platform platform : Platform.values()) {

            ConfigResponse configResponse = getConfigResponse(platform.getConfigUrl());
            String configUrl = configResponse.getConfig().getUrl() +
                    configResponse.getConfig().getVersion() +
                    ".json";
            ResponseEntity<PropertiesResponse> response = null;

            HttpHeaders headers = new HttpHeaders();
            //headers.set("X-C4-API-Key", configResponse.getApi().getRequestParameters().getApikey());
            headers.set("X-C4-API-Key", configResponse.getApi().getRequestHeaders().getxC4APIKey());

            HttpEntity entity = new HttpEntity(headers);

            if (isUrlValid(configUrl)) {
                response = restTemplate().exchange(URLDecoder.decode(configUrl), HttpMethod.GET, entity, PropertiesResponse.class);
                //response = restTemplate.getForEntity(URLDecoder.decode(configUrl, "UTF-8"), String.class);

                output += "<tr>"+
                        "<td>"+platform.getTitle()+"</td>"+
                        "<td>"+getVersion(platform.getVersionUrl())+"</td>"+
                        "<td>"+getSubtitles(response.getBody())+"</td>"+
                        "<td>"+getUserAlerts(response.getBody())+"</td>"+
                        "<td>"+getKantar(response.getBody())+"</td>"+
                        "<td>"+getCreditSqueeze(response.getBody())+"</td>"+
                        "<td>"+getAnimation(response.getBody())+"</td>"+
                        "<td>"+getConviva(response.getBody())+"</td>"+
                        "</tr>";
            } else {
                output += "<tr><td colspan=\"8\">Couldn't load the config file for "+platform.getTitle()+" from "+platform.getConfigUrl()+"</td></tr>";
            }


        }
        output += "</table></body></html>";

        return output;


    }

    public static boolean isUrlValid(String uri) {
        final URL url;
        try {
            url = new URL(uri);
        } catch (Exception e1) {
            return false;
        }
        return url.getProtocol().contains("http");
    }

    private String getSubtitles(PropertiesResponse properties) {
        Boolean subtitles;
        try {
            subtitles = properties.getConfig().getDisabledFeatures().getSubtitles();
            if (subtitles) {
                return cross();
            } else {
                return tick();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }


    private String getUserAlerts(PropertiesResponse properties) {
        Boolean userAlerts;
        try {
            userAlerts = properties.getConfig().getDisabledFeatures().getUserAlerts();
            if (userAlerts) {
                return cross();
            } else {
                return tick();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }


    private String getKantar(PropertiesResponse properties) {
        Boolean kantar;
        try {
            kantar = properties.getConfig().getDisabledFeatures().getKantar();
            if (kantar) {
                return cross();
            } else {
                return tick();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    private String getCreditSqueeze(PropertiesResponse properties) {
        Boolean creditSqueeze;
        try {
            creditSqueeze = properties.getConfig().getCreditSqueeze();
            if (creditSqueeze) {
                return tick();
            } else {
                return cross();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    private String getAnimation(PropertiesResponse properties) {
        Boolean animation;
        try {
            animation = properties.getConfig().getAnimation();
            if (animation) {
                return tick();
            } else {
                return cross();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    private String getConviva(PropertiesResponse properties) {
        Boolean conviva;
        try {
            conviva = properties.getConfig().getClientProperties().getConviva().getEnabled();
            if (conviva) {
                return tick();
            } else {
                return cross();
            }
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    private String tick() {
        //return "<i class=\"fa fa-check-circle\" style=\"color: green\" aria-hidden=\"true\"></i>";
        return "yes";
    }

    private String cross() {
        //return "<i class=\"fa fa-times\" style=\"color: grey\" aria-hidden=\"true\"></i>";
        return "";
    }


}

