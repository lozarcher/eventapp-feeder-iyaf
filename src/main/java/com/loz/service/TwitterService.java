package com.loz.service;

import com.loz.dao.TweetDao;
import com.loz.dao.model.TweetData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    TweetDao tweetDao;

    public List<TweetData> getTweets(Pageable pageable) {
        return tweetDao.findTweetsOrderByDate(pageable);
    }

    public long getTotal() {
        return tweetDao.count();
    }

}

