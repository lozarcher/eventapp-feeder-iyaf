package com.loz;

import com.loz.facebook.service.FacebookFeedService;
import com.loz.facebook.service.dao.EventDao;
import com.loz.facebook.service.dao.LastRefreshDao;
import com.loz.facebook.service.dao.feed.Cover;
import com.loz.facebook.service.dao.feed.Event;
import com.loz.facebook.service.dao.feed.EventResponse;
import com.loz.facebook.service.dao.feed.Events;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.facebook.service.dao.model.LastRefreshData;
import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.twitter.service.TwitterFeedService;
import com.loz.twitter.service.dao.TweetDao;
import com.loz.twitter.service.dao.domain.TweetData;
import com.loz.twitter.service.dao.feed.Status;
import com.loz.twitter.service.dao.feed.TwitterResponse;
import com.loz.twitter.service.dao.feed.User;
import com.loz.twitter.service.exception.TwitterAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    FacebookFeedService facebookFeedService;

    @Autowired
    TwitterFeedService twitterFeedService;

    @Autowired
    EventDao eventDao;

    @Autowired
    TweetDao tweetDao;

    @Autowired
    LastRefreshDao lastRefreshDao;

    @Scheduled(cron = "${cron.refresh.events}")
    public void refreshEvents() {
        LOGGER.debug("Refreshing Events");
        EventResponse eventsResponse;
        try {
            eventsResponse = facebookFeedService.getEvents();
        } catch (FacebookAccessException e) {
            LOGGER.error("Cannot retrieve events",e);
            throw new RuntimeException("Cannot retrieve events");
        }
        Events events = eventsResponse.getEvents();
        List<Event> eventList = events.getData();
        for (Event event : eventList) {
            EventData eventData = new EventData();
            eventData.setId(event.getId());
            eventData.setName(event.getName());
            eventData.setDescription(event.getDescription());
            Cover cover = event.getCover();
            if (cover != null) {
                eventData.setCoverUrl(cover.getSource());
            }
            eventData.setStartTime(event.getStart_time());
            eventData.setEndTime(event.getEnd_time());
            LOGGER.debug("Saving event {}", event.getName());
            eventDao.save(eventData);
        }

        updateLastRefresh("EVENT");
    }

    @Scheduled(cron = "${cron.refresh.tweets}")
    public void refreshTweets() {
        LOGGER.debug("Refreshing Tweets");
        TwitterResponse twitterResponse;
        try {
            twitterResponse = twitterFeedService.getTweets();
        } catch (TwitterAccessException e) {
            LOGGER.error("Cannot retrieve tweets",e);
            throw new RuntimeException("Cannot retrieve events");
        }
        List<Status> statuses = twitterResponse.getStatuses();
        for (Status status : statuses) {
            TweetData tweetData = new TweetData();
            tweetData.setId(Long.parseLong(status.getId_str()));
            User user = status.getUser();
            if (user != null) {
                tweetData.setName(user.getName());
                tweetData.setScreenName(user.getScreen_name());
            }
            tweetData.setText(status.getText());
            LOGGER.debug("Saving tweet {}", tweetData.getScreenName()+": "+tweetData.getText());
            tweetDao.save(tweetData);
        }

        updateLastRefresh("TWEET");
    }

    private void updateLastRefresh(String tableName) {
        LastRefreshData lastRefreshData = lastRefreshDao.findByTableName(tableName);
        if (lastRefreshData == null) {
            lastRefreshData = new LastRefreshData();
        }
        lastRefreshData.setTableName(tableName);
        lastRefreshData.setLastRefresh(new Date());
        lastRefreshDao.save(lastRefreshData);
    }
}
