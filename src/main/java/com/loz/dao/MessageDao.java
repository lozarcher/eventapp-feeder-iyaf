package com.loz.dao;

import com.loz.dao.model.MessageData;
import com.loz.dao.model.TweetData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageDao extends JpaRepository<MessageData, Long> {

    @Query("from MessageData order by createdDate desc")
    List<MessageData> findMessagesOrderByDate(Pageable pageable);
}

