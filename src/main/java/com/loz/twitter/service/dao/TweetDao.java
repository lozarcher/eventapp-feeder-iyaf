package com.loz.twitter.service.dao;

import com.loz.twitter.service.dao.domain.TweetData;
import org.springframework.data.repository.CrudRepository;

public interface TweetDao extends CrudRepository<TweetData, Long> {
}

