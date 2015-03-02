package com.loz.dao;

import com.loz.dao.model.TweetData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetDao extends CrudRepository<TweetData, Long> {
    @Query("from TweetData order by createdDate asc")
    List<TweetData> findAllOrderByDate();
}

