package com.loz.feeder.service;

import com.loz.feeder.dao.*;
import com.loz.feeder.dao.feed.facebook.event.Event;
import com.loz.feeder.dao.feed.facebook.event.EventResponse;
import com.loz.feeder.dao.feed.facebook.news.Post;
import com.loz.feeder.dao.feed.facebook.news.PostResponse;
import com.loz.feeder.dao.feed.facebook.page.Page;
import com.loz.feeder.dao.model.*;
import com.loz.feeder.exception.FacebookAccessException;
import com.loz.feeder.dao.feed.twitter.Status;
import com.loz.feeder.dao.feed.twitter.TwitterResponse;
import com.loz.feeder.exception.TwitterAccessException;
import org.joda.time.DateTime;
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
    PostDao postDao;

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

        List<Event> extraEvents = new ArrayList<Event>();
        // duplicate events for each day if they spread over multiple days
        for (Event event : eventList) {
            Date currentStartTime = event.getStart_time();
            Long currentId = event.getId();
            while (dateDiffMoreThanADay(currentStartTime, event.getEnd_time())) {
                Event newEvent = new Event();
                currentId++;
                newEvent.setId(currentId);
                newEvent.setCover(event.getCover());
                newEvent.setDescription(event.getDescription());
                newEvent.setEnd_time(event.getEnd_time());
                newEvent.setLocation(event.getLocation());
                newEvent.setName(event.getName());
                newEvent.setPicture(event.getPicture());
                newEvent.setTicket_uri(event.getTicket_uri());
                newEvent.setPlace(event.getPlace());

                DateTime startTimePrevious = new DateTime(currentStartTime);
                DateTime dtPlusOne = startTimePrevious.plusDays(1);
                newEvent.setStart_time(dtPlusOne.toDate());
                currentStartTime = dtPlusOne.toDate();
                extraEvents.add(newEvent);
                LOGGER.debug("Added extra event for "+newEvent.getName()+" on "+newEvent.getStart_time().toString());
            }
        }
        eventList.addAll(extraEvents);

        eventDao.deleteAll();

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

    public static boolean dateDiffMoreThanADay(Date startTime, Date endTime) {
        if (endTime == null) {
            return false;
        }
        long diffInMillies = endTime.getTime() - startTime.getTime();
        return (diffInMillies > 1000*60*60*24);
    }

    @Transactional
    public void updatePosts() {
        PostResponse postResponse;
        try {
            postResponse = facebookFeedService.getPosts();
        } catch (FacebookAccessException e) {
            LOGGER.error("Cannot retrieve posts",e);
            throw new RuntimeException("Cannot retrieve posts");
        }
        List<Post> postList = postResponse.getData();
        for (Post post : postList) {
            PostData postData = postDao.findOne(post.getId());
            if (postData == null) {
                postData = new PostData();
            }

            if (post.getMessage() != null) {
                post.setMessage(removeUTFCharacters(post.getMessage()));
            }
            postData.setDataFromPost(post);
            LOGGER.debug("Saving post {}", post.getMessage());
            postDao.save(postData);
        }
        updateLastRefresh("POST");
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
        return data.replaceAll("[^ -~\\\r\\\n]", "");
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
