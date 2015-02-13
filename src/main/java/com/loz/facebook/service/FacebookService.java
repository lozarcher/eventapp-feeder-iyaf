package com.loz.facebook.service;

import com.loz.facebook.service.dao.EventDao;
import com.loz.facebook.service.dao.model.EventData;
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

    public Iterable<EventData> getEvents() {
        return eventDao.findAll();
    }
}
