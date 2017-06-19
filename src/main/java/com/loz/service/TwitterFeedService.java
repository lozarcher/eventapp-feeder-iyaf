package com.loz.service;

import com.loz.dao.feed.twitter.TwitterResponse;
import com.loz.exception.TwitterAccessException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterFeedService {


    @Autowired
    private TwitterAccessTokenService twitterAccessTokenService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterFeedService.class);

    @Value("${twitter.url.search_tweets}")
    private String URL_SEARCH_TWEETS;

    @Value("${twitter.searchterm}")
    private String query;

    public TwitterResponse getTweets() throws TwitterAccessException {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = twitterAccessTokenService.getToken();
        if (accessToken == null) {
            throw new TwitterAccessException("Twitter access token is null");
        }
        LOGGER.debug("Requesting from URL " + URL_SEARCH_TWEETS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<TwitterResponse> response;
        try {
            response = restTemplate.exchange(URL_SEARCH_TWEETS, HttpMethod.GET, request, TwitterResponse.class, query);
        } catch (Exception e) {
            throw new TwitterAccessException("Cannot reach Twitter: ");
        }
        LOGGER.debug("Received tweets from Twitter: " + response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        } else {
            throw new TwitterAccessException("Status code "+response.getStatusCode());
        }
    }

}

