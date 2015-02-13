package com.loz.facebook.service.dao;


import com.loz.facebook.service.dao.model.EventData;
import org.springframework.data.repository.CrudRepository;

public interface EventDao extends CrudRepository<EventData, Long> {
}

