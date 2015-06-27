package com.loz.service;

import com.loz.dao.EventDao;
import com.loz.dao.PerformerDao;
import com.loz.dao.PostDao;
import com.loz.dao.VenueDao;
import com.loz.dao.model.*;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by larcher on 13/02/2015.
 */
@Service
public class FacebookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookService.class);

    @Autowired
    EventDao eventDao;

    @Autowired
    PostDao postDao;

    @Autowired
    VenueDao venueDao;

    @Autowired
    PerformerDao performerDao;

    public Iterable<EventData> getEvents() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -6);
        LOGGER.error("Getting events since {}", cal.getTime());
        return eventDao.findAllOrderByDate(cal.getTime());
    }

    public List<PostData> getPosts(Pageable pageable) {
        return postDao.findPostsOrderByDate(pageable);
    }

    public long getTotalPosts() {
        return postDao.count();
    }

    public Iterable<VenueData> getVenues() {
        Iterable<VenueData> venues = venueDao.findAll();
        for (VenueData venue : venues) {
            venue.setLocation(getLocationForVenue(venue));
        }
        return venues;
    }

    public Iterable<PerformerData> getPerformers() {
        Iterable<PerformerData> traders = performerDao.findAllOrderByKPoundAndName();
        return traders;
    }

    private String getLocationForVenue(VenueData venueData) {
        List<EventData> events = eventDao.findByVenueId(venueData);
        if (events.size() > 0) {
            return events.get(0).getLocation();
        } else {
            return null;
        }
    }

    public String getEventsAsICal() {
        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//IYAF 2015//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -6);
        List<EventData> events = eventDao.findAllOrderByDate(cal.getTime());

        for (EventData event : events) {
            VEvent vEvent;
            Location location = new Location(event.getLocation());
            DateTime start = new DateTime(event.getStartTime());
            if (event.getEndTime() != null) {
                DateTime end = new DateTime(event.getEndTime());
                vEvent = new VEvent(start, end, event.getName());
            } else {
                vEvent = new VEvent(start, event.getName());
            }
            Uid uid = new Uid(event.getId().toString());
            vEvent.getProperties().add(uid);
            vEvent.getProperties().add(location);
            calendar.getComponents().add(vEvent);
        }
        return calendar.toString();
    }
}
