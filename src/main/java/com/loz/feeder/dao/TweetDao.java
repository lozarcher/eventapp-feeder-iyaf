package com.loz.feeder.dao;

import com.loz.feeder.dao.model.TweetData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TweetDao extends JpaRepository<TweetData, Long> {

    @Query("from TweetData order by createdDate desc")
    List<TweetData> findTweetsOrderByDate(Pageable pageable);
}

