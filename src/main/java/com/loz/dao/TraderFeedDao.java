package com.loz.dao;

import com.loz.dao.model.PerformerFeedData;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by loz on 23/02/15.
 */
public interface TraderFeedDao extends CrudRepository<PerformerFeedData, Long> {
}
