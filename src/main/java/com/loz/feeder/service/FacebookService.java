package com.loz.feeder.service;
import com.loz.feeder.dao.*;
import com.loz.feeder.dao.model.*;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
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
    PostDao postDao;

    @Autowired
    VenueDao venueDao;

    @Autowired
    PerformerDao performerDao;

    @Autowired
    GalleryDao galleryDao;

    @Autowired
    InfoDao infoDao;

    @Value("${gallery.moderate}")
    private boolean galleryModerate;

    public Iterable<EventData> getEvents() {
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.DAY_OF_MONTH, 7);
        //cal.set(Calendar.MONTH, Calendar.JULY);
        //cal.set(Calendar.YEAR, 2016);
        //cal.set(Calendar.HOUR_OF_DAY, 0);
        //cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.MONTH, -12);
        LOGGER.info("Getting events since {}", cal.getTime());
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

    public Iterable<GalleryData> getGallery() {
        if (galleryModerate) {
            return galleryDao.findModeratedOrderByDate();
        } else {
            return galleryDao.findAllOrderByDate();
        }
    }

    public Iterable<InfoData> getInfo() {
        return infoDao.findAllOrderByTitle();
    }

    public GalleryData saveGallery(GalleryData galleryData) {
        return galleryDao.save(galleryData);
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