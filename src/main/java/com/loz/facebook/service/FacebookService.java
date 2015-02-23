package com.loz.facebook.service;

import com.loz.facebook.service.dao.EventDao;
import com.loz.facebook.service.dao.VenueDao;
import com.loz.facebook.service.dao.model.EventData;
import com.loz.facebook.service.dao.model.VenueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by larcher on 13/02/2015.
 */
@Service
public class FacebookService {

    @Autowired
    EventDao eventDao;

    @Autowired
    VenueDao venueDao;

    public Iterable<EventData> getEvents() {
        return eventDao.findAllOrderByDate();
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
