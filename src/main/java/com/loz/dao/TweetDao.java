package com.loz.dao;

import com.loz.dao.model.TweetData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
public interface TweetDao extends JpaRepository<TweetData, Long> {

    @Query("from TweetData order by createdDate desc")
    List<TweetData> findTweetsOrderByDate(Pageable pageable);
}

