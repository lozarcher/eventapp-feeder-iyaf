package com.loz.shared.dao.shared.service;

import com.loz.facebook.service.FacebookFeedService;
import com.loz.facebook.service.dao.EventDao;
import com.loz.facebook.service.dao.LastRefreshDao;
import com.loz.facebook.service.dao.VenueDao;
import com.loz.facebook.service.dao.feed.Event;
import com.loz.facebook.service.dao.feed.EventResponse;
import com.loz.facebook.service.dao.feed.Venue;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.facebook.service.dao.model.LastRefreshData;
import com.loz.facebook.service.dao.model.VenueData;
import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.twitter.service.TwitterFeedService;
import com.loz.twitter.service.dao.TweetDao;
import com.loz.twitter.service.dao.domain.TweetData;
import com.loz.twitter.service.dao.feed.Status;
import com.loz.twitter.service.dao.feed.TwitterResponse;
import com.loz.twitter.service.exception.TwitterAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                eventData = new EventData(event);
            }
            LOGGER.debug("Saving event {}", event.getName());
            eventDao.save(eventData);
        }
        updateLastRefresh("EVENT");
        updateLastRefresh("VENUE");
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
            TweetData tweetData = new TweetData(status);
            LOGGER.debug("Saving tweet {}", tweetData.getScreenName()+": "+tweetData.getText());
            tweetDao.save(tweetData);
        }
        updateLastRefresh("TWEET");
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
