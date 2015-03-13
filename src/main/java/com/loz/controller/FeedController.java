package com.loz.controller;

import com.loz.dao.responseVo.*;
import com.loz.service.FacebookService;
import com.loz.service.TwitterFeedService;
import com.loz.service.TwitterService;
import com.loz.service.VoucherService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class FeedController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    VoucherService voucherService;

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

    @RequestMapping("/vouchers")
    @ResponseBody
    public VoucherResponse vouchers() {
        VoucherResponse response = new VoucherResponse();
        response.setDate(new Date());
        response.setData(voucherService.getVouchers());
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

    @RequestMapping("/traders")
    @ResponseBody
    public PageResponse traders() {
        PageResponse response = new PageResponse();
        response.setDate(new Date());
        response.setData(facebookService.getTraders());
        return response;
    }

    @RequestMapping("/venues")
    @ResponseBody
    public VenueResponse venues() {
        VenueResponse response = new VenueResponse();
        response.setDate(new Date());
        response.setData(facebookService.getVenues());
        return response;
    }

}
