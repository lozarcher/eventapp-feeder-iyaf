package com.loz.service;

import com.loz.exception.FacebookAccessTokenNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookAccessTokenService {

    @Value("${facebook.appId}")
    private String appId;
    @Value("${facebook.appSecret}")
    private String appSecret;

    private String token;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FacebookFeedService.class);

    @Value("${facebook.url.get_token}")
    private String URL_GET_TOKEN;

    public String getToken() {
        if (token == null) {
            try {
                token = getNewToken();
            } catch (FacebookAccessTokenNotFoundException e) {
                LOGGER.error("Facebook Access token not found with credentials");
            }
        }
        return token;
    }

    private String getNewToken() throws FacebookAccessTokenNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        String getTokenUrl = String.format(URL_GET_TOKEN, appId, appSecret);

        LOGGER.debug("URL = "+getTokenUrl);
        ResponseEntity response = restTemplate.getForEntity(getTokenUrl, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String[] tokenParts = response.getBody().toString().split("=");
            if (tokenParts[1] != null) {
                return tokenParts[1];
            } else {
                throw new FacebookAccessTokenNotFoundException("Malformed response: "+response.getBody().toString());
            }
        } else {
            throw new FacebookAccessTokenNotFoundException("Status code "+response.getStatusCode());
        }
    }
}

