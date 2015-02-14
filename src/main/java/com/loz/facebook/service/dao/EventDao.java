package com.loz.facebook.service.dao;


import com.loz.facebook.service.dao.model.EventData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventDao extends CrudRepository<EventData, Long> {

    @Query("from EventData order by startTime asc")
    List<EventData> findAllOrderByDate();
}

