package com.loz.service;

import com.loz.dao.*;
import com.loz.dao.feed.facebook.Event;
import com.loz.dao.feed.facebook.EventResponse;
import com.loz.dao.feed.facebook.Page;
import com.loz.dao.model.EventData;
import com.loz.dao.model.LastRefreshData;
import com.loz.dao.model.PerformerData;
import com.loz.exception.FacebookAccessException;
import com.loz.dao.model.TweetData;
import com.loz.dao.feed.twitter.Status;
import com.loz.dao.feed.twitter.TwitterResponse;
import com.loz.exception.TwitterAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by loz on 23/02/15.
 */
@Service
public class RefreshService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshService.class);

    @Autowired
    FacebookFeedService facebookFeedService;

    @Autowired
    TwitterFeedService twitterFeedService;

    @Autowired
    EventDao eventDao;

    @Autowired
    VenueDao venueDao;

    @Autowired
    TweetDao tweetDao;

    @Autowired
    PerformerDao performerDao;

    @Autowired
    LastRefreshDao lastRefreshDao;

    @Transactional
    public void updateEventsAndVenues() {
        EventResponse eventsResponse;
        try {
            eventsResponse = facebookFeedService.getEvents();
        } catch (FacebookAccessException e) {
            LOGGER.error("Cannot retrieve events",e);
            throw new RuntimeException("Cannot retrieve events");
        }
        List<Event> eventList = eventsResponse.getData();
        for (Event event : eventList) {
            EventData eventData = eventDao.findOne(event.getId());
            if (eventData == null) {
                eventData = new EventData();
            }
            eventData.setDataFromEvent(event);
            LOGGER.debug("Saving event {}", event.getName());
            eventDao.save(eventData);
        }
        updateLastRefresh("EVENT");
        updateLastRefresh("VENUE");
    }

    @Transactional
    public void updatePerformers() {
        List<Page> traders = new ArrayList<Page>();
        try {
            traders = facebookFeedService.getPages();
        } catch (FacebookAccessException e) {
            LOGGER.error("Cannot retrieve performer",e);
            throw new RuntimeException("Cannot retrieve performer");
        }
        for (Page page : traders) {
            PerformerData performerData = new PerformerData(page);
            LOGGER.debug("Saving performer {}", performerData.getName());
            performerDao.save(performerData);
        }
        updateLastRefresh("PERFORMER");
    }


    @Transactional
    public void updateTweets() {
        TwitterResponse twitterResponse;
        try {
            twitterResponse = twitterFeedService.getTweets();
        } catch (TwitterAccessException e) {
            LOGGER.error("Cannot retrieve tweets",e);
            throw new RuntimeException("Cannot retrieve tweets");
        }
        List<Status> statuses = twitterResponse.getStatuses();
        for (Status status : statuses) {
            status.setText(removeUTFCharacters(status.getText()));
            TweetData tweetData = new TweetData(status);
            LOGGER.debug("Saving tweet {}", tweetData.getScreenName()+": "+tweetData.getText());
            tweetDao.save(tweetData);
        }
        updateLastRefresh("TWEET");
    }

    public static String removeUTFCharacters(String data){
        return data.replaceAll("[^ -~]", "");
    }

    private void updateLastRefresh(String tableName) {
        LastRefreshData lastRefreshData = lastRefreshDao.findByTableName(tableName);
        if (lastRefreshData == null) {
            lastRefreshData = new LastRefreshData();
            lastRefreshData.setId(null);
        }
        lastRefreshData.setTableName(tableName);
        lastRefreshData.setLastRefresh(new Date());
        lastRefreshDao.save(lastRefreshData);
    }
}
