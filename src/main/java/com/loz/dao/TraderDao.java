package com.loz.dao;

import com.loz.dao.feed.facebook.Page;
import com.loz.dao.model.EventData;
import com.loz.dao.model.TraderData;
import com.loz.dao.model.VenueData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by loz on 23/02/15.
 */
public interface TraderDao extends CrudRepository<TraderData, Long> {
    @Query("from TraderData order by name asc")
    List<TraderData> findAllOrderByKPoundAndName();
}
