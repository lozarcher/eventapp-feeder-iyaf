package com.loz.feeder.service;

import com.loz.feeder.dao.TraderFeedDao;
import com.loz.feeder.dao.feed.facebook.event.EventResponse;
import com.loz.feeder.dao.feed.facebook.news.PostResponse;
import com.loz.feeder.dao.feed.facebook.page.Page;
import com.loz.feeder.dao.model.PerformerFeedData;
import com.loz.feeder.exception.FacebookAccessException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacebookFeedService {

    @Value("${facebook.pageId}")
    private String pageId;

    @Autowired
    private FacebookAccessTokenService facebookAccessTokenService;

    @Autowired
    private TraderFeedDao traderFeedDao;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FacebookFeedService.class);

    @Value("${facebook.url.get_events}")
    private String URL_GET_EVENTS;

    @Value("${facebook.url.get_events.since}")
    private String URL_GET_EVENTS_SINCE;

    @Value("${facebook.url.get_posts.filters}")
    private String URL_GET_POSTS_FILTERS;

    @Value("${facebook.url.get_posts}")
    private String URL_GET_POSTS;

    @Value("${facebook.url.get_posts.since}")
    private String URL_GET_POSTS_SINCE;

    @Value("${facebook.url.get_events.filters}")
    private String URL_GET_EVENTS_FILTERS;

    @Value("${facebook.url.get_page}")
    private String URL_GET_PAGE;

    @Value("${facebook.url.get_page.filters}")
    private String URL_GET_PAGE_FILTERS;

    public Page getPage(Long pageId) throws FacebookAccessException {
        Page page = new Page();
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();
        String getPageUrl = String.format(URL_GET_PAGE, pageId, URL_GET_PAGE_FILTERS, accessToken);
        ResponseEntity<Page> response = null;
        LOGGER.debug("Requesting from URL "+getPageUrl);

        try {
            response = restTemplate.getForEntity(URLDecoder.decode(getPageUrl, "UTF-8"), Page.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URL syntax invalid " + getPageUrl);
            throw new FacebookAccessException(e.getMessage());
        }
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.error("Facebook access error");
            throw new FacebookAccessException("Status code " + response.getStatusCode());
        }
        return response.getBody();
    }

    public List<Page> getPages() throws FacebookAccessException {
        Iterable<PerformerFeedData> performerList = traderFeedDao.findAll();
        List<Page> performerPages = new ArrayList<Page>();
        for (PerformerFeedData performerFeed : performerList) {
            Page performerPage = getPage(performerFeed.getId());
            performerPage.setKingstonPound(performerFeed.iskPound());
            performerPages.add(performerPage);
        }
        return performerPages;
    }

    public PostResponse getPosts() throws FacebookAccessException {
        PostResponse paginatedPostResponse = new PostResponse();
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();

        boolean lastPage = false;

        String getPostsUrl = String.format(URL_GET_POSTS, pageId, URL_GET_POSTS_FILTERS, accessToken);
        getPostsUrl += "&since="+URL_GET_POSTS_SINCE;
        LOGGER.debug("Requesting from URL "+getPostsUrl);

        while (lastPage == false) {
            ResponseEntity<PostResponse> response = null;
            try {
                response = restTemplate.getForEntity(URLDecoder.decode(getPostsUrl, "UTF-8"), PostResponse.class);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("URL syntax invalid " + getPostsUrl);
                throw new FacebookAccessException(e.getMessage());
            }
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                LOGGER.error("Facebook access error");
                throw new FacebookAccessException("Status code "+response.getStatusCode());
            }
            if (paginatedPostResponse.getData() == null) {
                paginatedPostResponse = response.getBody();
            } else {
                paginatedPostResponse.addData(response.getBody().getData());
            }


            lastPage = true;
            if (response.getBody().getPaging() != null) {
                String nextPage = response.getBody().getPaging().getNext();
                if (nextPage != null) {
                    lastPage = false;
                    nextPage += "&since="+URL_GET_POSTS_SINCE;
                    getPostsUrl = nextPage;
                } else {
                    lastPage = true;
                }
            }
        }
        return paginatedPostResponse;
    }

    public EventResponse getEvents() throws FacebookAccessException {
        EventResponse paginatedEventResponse = new EventResponse();
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = facebookAccessTokenService.getToken();

        boolean lastPage = false;

        String getEventsUrl = String.format(URL_GET_EVENTS, pageId, URL_GET_EVENTS_FILTERS, accessToken);
        getEventsUrl += "&since="+URL_GET_EVENTS_SINCE;
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
                    nextPage += "&since="+URL_GET_EVENTS_SINCE;
                    getEventsUrl = nextPage;
                } else {
                    lastPage = true;
                }
            }
        }
        return paginatedEventResponse;
    }

}

