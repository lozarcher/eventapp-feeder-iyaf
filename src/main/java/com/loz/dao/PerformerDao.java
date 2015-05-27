package com.loz.dao;

import com.loz.dao.model.PerformerData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by loz on 23/02/15.
 */
public interface PerformerDao extends CrudRepository<PerformerData, Long> {
    @Query("from PerformerData order by name asc")
    List<PerformerData> findAllOrderByKPoundAndName();
}
