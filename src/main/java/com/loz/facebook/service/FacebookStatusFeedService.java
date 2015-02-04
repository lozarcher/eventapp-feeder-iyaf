package com.loz.facebook.service;

import com.loz.facebook.service.dao.EventResponse;
import com.loz.facebook.service.exception.FacebookAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class FacebookStatusFeedService {

    @Value("${spring.social.facebook.pageId}")
    private String pageId;

    @Autowired
    private FacebookAccessTokenService facebookAccessTokenService;

    private Logger logger = Logger.getAnonymousLogger();

    public String URL_GET_EVENTS = "https://graph.facebook.com/v2.2/{pageId}?fields=events{filters}&access_token={accessToken}";

    public EventResponse getEvents() throws FacebookAccessException {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();
        String filters = "{end_time,description,id,name,noreply_count,cover}";

        ResponseEntity<EventResponse> response = restTemplate.getForEntity(URL_GET_EVENTS, EventResponse.class, pageId, filters, accessToken);
        logger.info("Received events from Facebook: "+response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        } else {
            throw new FacebookAccessException("Status code "+response.getStatusCode());
        }
    }
}

