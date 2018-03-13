package com.loz.feeder.dao;

import com.loz.feeder.dao.model.LastRefreshData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LastRefreshDao extends CrudRepository<LastRefreshData, Long> {
    @Query("from LastRefreshData where tableName = ?1")
    LastRefreshData findByTableName(final String tableName);
}
