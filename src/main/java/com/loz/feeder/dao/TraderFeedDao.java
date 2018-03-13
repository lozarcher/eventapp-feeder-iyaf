package com.loz.feeder.dao;

import com.loz.feeder.dao.model.PerformerFeedData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface TraderFeedDao extends CrudRepository<PerformerFeedData, Long> {
}
