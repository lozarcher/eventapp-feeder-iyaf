package com.loz.controller;

import com.loz.facebook.service.FacebookService;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.shared.dao.EventResponse;
import com.loz.shared.dao.TweetResponse;
import com.loz.twitter.service.TwitterFeedService;
import com.loz.twitter.service.TwitterService;
import com.loz.twitter.service.dao.domain.TweetData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FeedController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    TwitterFeedService twitterFeedService;

    @RequestMapping("/events")
    @ResponseBody
    public EventResponse events() {
        EventResponse response = new EventResponse();
        response.setDate(new Date());
        response.setData(facebookService.getEvents());
        // response.setData(new ArrayList<EventData>());   // Empty response for testing UI
        return response;
    }

    @RequestMapping("/tweets")
    @ResponseBody
    public TweetResponse tweets() {
        TweetResponse response = new TweetResponse();
        response.setDate(new Date());
        response.setData(twitterService.getTweets());
        return response;
    }

}
