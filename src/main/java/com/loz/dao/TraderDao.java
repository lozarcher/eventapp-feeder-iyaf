package com.loz.dao;

import com.loz.dao.feed.facebook.Page;
import com.loz.dao.model.TraderData;
import com.loz.dao.model.VenueData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface TraderDao extends CrudRepository<TraderData, Long> {
}
