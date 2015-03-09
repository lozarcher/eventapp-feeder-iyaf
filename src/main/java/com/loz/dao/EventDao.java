package com.loz.dao;


import com.loz.dao.model.EventData;
import com.loz.dao.model.VenueData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventDao extends CrudRepository<EventData, Long> {

    @Query("from EventData where startTime > ?1 order by startTime asc")
    List<EventData> findAllOrderByDate(Date dateSince);

    @Query("from EventData where venue=?1")
    List<EventData> findByVenueId(VenueData venue);
}

