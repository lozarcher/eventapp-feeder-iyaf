package com.loz.dao;

import com.loz.dao.model.TraderData;
import com.loz.dao.model.TraderFeedData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface TraderFeedDao extends CrudRepository<TraderFeedData, Long> {
}
