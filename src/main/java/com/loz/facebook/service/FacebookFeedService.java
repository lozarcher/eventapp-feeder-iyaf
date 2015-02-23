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

import java.io.UnsupportedEncodingException;
import java.net.*;
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

    @Value("${facebook.url.get_events.filters}")
    private String URL_GET_EVENTS_FILTERS;

    public EventResponse getEvents() throws FacebookAccessException {
        EventResponse paginatedEventResponse = new EventResponse();
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();

        boolean lastPage = false;

        String getEventsUrl = String.format(URL_GET_EVENTS, pageId, URL_GET_EVENTS_FILTERS, accessToken);
        LOGGER.debug("Requesting from URL "+getEventsUrl);

        while (lastPage == false) {
            ResponseEntity<EventResponse> response = null;
            try {
                response = restTemplate.getForEntity(URLDecoder.decode(getEventsUrl, "UTF-8"), EventResponse.class);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("URL syntax invalid " + getEventsUrl);
                throw new FacebookAccessException(e.getMessage());
            }
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                LOGGER.error("Facebook access error");
                throw new FacebookAccessException("Status code "+response.getStatusCode());
            }
            if (paginatedEventResponse.getData() == null) {
                paginatedEventResponse = response.getBody();
            } else {
                paginatedEventResponse.addData(response.getBody().getData());
            }


            lastPage = true;
            if (response.getBody().getPaging() != null) {
                String nextPage = response.getBody().getPaging().getNext();
                if (nextPage != null) {
                    lastPage = false;
                    getEventsUrl = nextPage;
                } else {
                    lastPage = true;
                }
            }
        }
        return paginatedEventResponse;
    }
}

