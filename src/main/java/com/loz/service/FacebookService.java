package com.loz.service;

import com.loz.dao.EventDao;
import com.loz.dao.VenueDao;
import com.loz.dao.model.EventData;
import com.loz.dao.model.VenueData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by larcher on 13/02/2015.
 */
@Service
public class FacebookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookService.class);

    @Autowired
    EventDao eventDao;

    @Autowired
    VenueDao venueDao;

    @Value("${facebook.get_events.since}")
    private String GET_EVENTS_SINCE;

    public Iterable<EventData> getEvents() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
        Date sinceDate = null;
        try {
            sinceDate = format.parse(GET_EVENTS_SINCE);
        } catch (ParseException e) {
            LOGGER.error("Cannot parse date {}", GET_EVENTS_SINCE);
            return eventDao.findAllOrderByDate(new Date());
        }
        LOGGER.error("Getting events since {}", sinceDate);

        return eventDao.findAllOrderByDate(sinceDate);
    }

    public Iterable<VenueData> getVenues() {
        Iterable<VenueData> venues = venueDao.findAll();
        for (VenueData venue : venues) {
            venue.setLocation(getLocationForVenue(venue));
        }
        return venues;
    }

    private String getLocationForVenue(VenueData venueData) {
        List<EventData> events = eventDao.findByVenueId(venueData);
        if (events.size() > 0) {
            return events.get(0).getLocation();
        } else {
            return null;
        }
    }
}
