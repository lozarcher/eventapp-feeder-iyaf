package com.loz.controller;

import com.loz.facebook.service.FacebookFeedService;
import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.facebook.service.dao.EventResponse;
import com.loz.twitter.service.TwitterFeedService;
import com.loz.twitter.service.dao.TwitterResponse;
import com.loz.twitter.service.exception.TwitterAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class FeedController {

    private Logger logger = Logger.getAnonymousLogger();

    @Autowired
    FacebookFeedService facebookFeedService;

    @Autowired
    TwitterFeedService twitterFeedService;

    @RequestMapping("/events")
    @ResponseBody
    public EventResponse events() {
        try {
            return facebookFeedService.getEvents();
        } catch (FacebookAccessException e) {
            logger.severe("Could not get events");
            return null;
        }
    }

    @RequestMapping("/tweets")
    @ResponseBody
    public TwitterResponse tweets() {
        try {
            return twitterFeedService.getTweets();
        } catch (TwitterAccessException e) {
            logger.severe("Could not get tweets");
            return null;
        }
    }

}
