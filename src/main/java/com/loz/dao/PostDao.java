package com.loz.dao;

import com.loz.dao.model.PostData;
import com.loz.dao.model.TweetData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<PostData, Long> {

    @Query("from PostData order by createdDate desc")
    List<PostData> findPostsOrderByDate(Pageable pageable);
}

