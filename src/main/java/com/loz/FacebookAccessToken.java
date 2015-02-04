package com.loz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class FacebookAccessToken {

    @Value("${spring.social.facebook.appId}")
    private String appId;
    @Value("${spring.social.facebook.appSecret}")
    private String appSecret;

    private Logger logger = Logger.getAnonymousLogger();

    public String URL_GET_TOKEN = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&grant_type=client_credentials";

    public String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        String getTokenUrl = String.format(URL_GET_TOKEN, appId, appSecret);

        logger.info("URL = "+getTokenUrl);
        return restTemplate.getForEntity(getTokenUrl, String.class).toString();

    }
}

