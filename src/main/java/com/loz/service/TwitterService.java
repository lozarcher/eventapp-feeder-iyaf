package com.loz.service;

import com.loz.dao.TweetDao;
import com.loz.dao.model.TweetData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    TweetDao tweetDao;

    public Iterable<TweetData> getTweets() {
        return tweetDao.findAll();
    }

}

