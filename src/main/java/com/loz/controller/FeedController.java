package com.loz.controller;

import com.loz.dao.model.MessageData;
import com.loz.dao.model.TweetData;
import com.loz.dao.responseVo.*;
import com.loz.service.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FeedController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FeedController.class);

    @Value("${twitter.pagesize}")
    private int PAGESIZE;

    @Autowired
    FacebookService facebookService;

    @Autowired
    TwitterService twitterService;

    @Autowired
    MessageService messageService;

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

    @RequestMapping("/tweets/{offset}")
    @ResponseBody
    public TweetResponse tweetsPaginated(@PathVariable("offset") int page) {
        TweetResponse response = new TweetResponse();
        response.setDate(new Date());
        Pageable pageable = new PageRequest(page, PAGESIZE);
        List<TweetData> tweets = twitterService.getTweets(pageable);
        response.setData(tweets);
        long total = twitterService.getTotal();
        if ((page * PAGESIZE) + tweets.size() < total) {
            page++;
            response.setNext("/tweets/"+page);
        } else {
            response.setNext(null);
        }
        return response;
    }

    @RequestMapping("/tweets")
    @ResponseBody
    public TweetResponse tweets() {
        return tweetsPaginated(0);
    }

    @RequestMapping("/messages/{offset}")
    @ResponseBody
    public MessageResponse messagesPaginated(@PathVariable("offset") int page) {
        MessageResponse response = new MessageResponse();
        response.setDate(new Date());
        Pageable pageable = new PageRequest(page, PAGESIZE);
        List<MessageData> messages = messageService.getMessages(pageable);
        response.setData(messages);
        long total = messageService.getTotal();
        if ((page * PAGESIZE) + messages.size() < total) {
            page++;
            response.setNext("/messages/"+page);
        } else {
            response.setNext(null);
        }
        return response;
    }

    @RequestMapping("/messages")
    @ResponseBody
    public MessageResponse messages() {
        return messagesPaginated(0);
    }

    @RequestMapping(value="/messages/post" , method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<MessageData> addNewWorker(@RequestBody MessageData jsonString) {
        MessageData messageData = jsonString;
        LOGGER.debug("Received post with JSON: %s", jsonString);
        messageData.setCreatedDate(new Date());
        // Test for malformed json
        MessageData savedMessage = new MessageData();
        if (jsonString != null) {
            savedMessage = messageService.saveMessage(jsonString.getName(), jsonString.getText(), jsonString.getProfilePic());
            if (savedMessage != null) {
                return new ResponseEntity<MessageData>(savedMessage, HttpStatus.OK);
            }

        }
        return new ResponseEntity<MessageData>(savedMessage, HttpStatus.INTERNAL_SERVER_ERROR);

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
