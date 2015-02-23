package com.loz;

import com.loz.facebook.service.FacebookFeedService;
import com.loz.facebook.service.dao.EventDao;
import com.loz.facebook.service.dao.LastRefreshDao;
import com.loz.facebook.service.dao.VenueDao;
import com.loz.facebook.service.dao.feed.Cover;
import com.loz.facebook.service.dao.feed.Event;
import com.loz.facebook.service.dao.feed.EventResponse;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.facebook.service.dao.model.LastRefreshData;
import com.loz.facebook.service.exception.FacebookAccessException;
import com.loz.shared.dao.shared.service.RefreshService;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    LastRefreshDao lastRefreshDao;

    @Autowired
    RefreshService refreshService;

    @Scheduled(cron = "${cron.refresh.events}")
    public void refreshEvents() {
        LOGGER.debug("Refreshing Events");
        refreshService.updateEventsAndVenues();
    }

    @Scheduled(cron = "${cron.refresh.tweets}")
    public void refreshTweets() {
        LOGGER.debug("Refreshing Tweets");
        refreshService.updateTweets();
    }
}
