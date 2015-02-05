package com.loz.twitter.service;

import com.loz.facebook.service.FacebookAccessTokenService;
import com.loz.facebook.service.dao.EventResponse;
import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.twitter.service.dao.TwitterResponse;
import com.loz.twitter.service.exception.TwitterAccessException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.logging.Logger;

@Service
public class TwitterFeedService {


    @Autowired
    private TwitterAccessTokenService twitterAccessTokenService;

    private Logger logger = Logger.getAnonymousLogger();

    @Value("${twitter.url.search_tweets}")
    private String URL_SEARCH_TWEETS;

    @Value("${twitter.hashtag}")
    private String query;

    public TwitterResponse getTweets() throws TwitterAccessException {
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = twitterAccessTokenService.getToken();
        if (accessToken == null) {
            throw new TwitterAccessException("Twitter access token is null");
        }
        logger.info("Requesting from URL "+URL_SEARCH_TWEETS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<TwitterResponse> response;
        try {
            response = restTemplate.exchange(URL_SEARCH_TWEETS, HttpMethod.GET, request, TwitterResponse.class, query);
        } catch (Exception e) {
            throw new TwitterAccessException("Cannot reach Twitter: ");
        }
        logger.info("Received tweets from Twitter: " + response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        } else {
            throw new TwitterAccessException("Status code "+response.getStatusCode());
        }
    }

}

