package com.loz.controller;

import com.loz.facebook.service.FacebookService;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.twitter.service.TwitterFeedService;
import com.loz.twitter.service.TwitterService;
import com.loz.twitter.service.dao.domain.TweetData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Iterable<EventData> events() {
        return facebookService.getEvents();
    }

    @RequestMapping("/tweets")
    @ResponseBody
    public Iterable<TweetData> tweets() {
        return twitterService.getTweets();
    }

}
