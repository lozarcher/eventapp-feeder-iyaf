package com.loz.controller;

import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.facebook.service.FacebookStatusFeedService;
import com.loz.facebook.service.dao.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class FeedController {

    private Logger logger = Logger.getAnonymousLogger();

    @Autowired
    FacebookStatusFeedService facebookStatusFeedService;

    @RequestMapping("/events")
    @ResponseBody
    public EventResponse hello() {
        try {
            return facebookStatusFeedService.getEvents();
        } catch (FacebookAccessException e) {
            logger.severe("Could not get events");
            return null;
        }
    }
}
