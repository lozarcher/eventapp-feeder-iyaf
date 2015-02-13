package com.loz.facebook.service;

import com.loz.facebook.service.dao.feed.EventResponse;
import com.loz.facebook.service.exception.FacebookAccessException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class FacebookFeedService {

    @Value("${facebook.pageId}")
    private String pageId;

    @Autowired
    private FacebookAccessTokenService facebookAccessTokenService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FacebookFeedService.class);

    @Value("${facebook.url.get_events}")
    private String URL_GET_EVENTS;

    public EventResponse getEvents() throws FacebookAccessException {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();
        String filters = "{end_time,description,id,name,noreply_count,cover}";

        LOGGER.debug("Requesting from URL "+URL_GET_EVENTS);
        ResponseEntity<EventResponse> response = restTemplate.getForEntity(URL_GET_EVENTS, EventResponse.class, pageId, filters, accessToken);
        LOGGER.debug("Received events from Facebook: "+response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        } else {
            throw new FacebookAccessException("Status code "+response.getStatusCode());
        }
    }
}

