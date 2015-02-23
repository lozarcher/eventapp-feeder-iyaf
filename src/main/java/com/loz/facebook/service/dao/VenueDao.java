package com.loz.facebook.service.dao;

import com.loz.facebook.service.dao.model.VenueData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface VenueDao extends CrudRepository<VenueData, Long> {
}
