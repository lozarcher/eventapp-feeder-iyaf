package com.loz.feeder.dao;

import com.loz.feeder.dao.model.VenueData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface VenueDao extends CrudRepository<VenueData, Long> {
}
