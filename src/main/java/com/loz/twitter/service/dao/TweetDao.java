package com.loz.twitter.service.dao;

import com.loz.twitter.service.dao.domain.TweetData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetDao extends CrudRepository<TweetData, Long> {
    @Query("from TweetData order by createdDate desc")
    List<TweetData> findAllOrderByDate();
}

