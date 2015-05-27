package com.loz.service;

import com.loz.dao.EventDao;
import com.loz.dao.PerformerDao;
import com.loz.dao.VenueDao;
import com.loz.dao.model.EventData;
import com.loz.dao.model.PerformerData;
import com.loz.dao.model.VenueData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    VenueDao venueDao;

    @Autowired
    PerformerDao performerDao;

    public Iterable<EventData> getEvents() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        LOGGER.error("Getting events since {}", cal.getTime());
        return eventDao.findAllOrderByDate(cal.getTime());
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
}
