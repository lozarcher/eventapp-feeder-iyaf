package com.loz.twitter.service;

import com.loz.twitter.service.dao.TweetDao;
import com.loz.twitter.service.dao.domain.TweetData;
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

