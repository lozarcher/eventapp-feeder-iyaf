package com.loz.feeder.dao;


import com.loz.feeder.dao.model.VoucherData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoucherDao extends CrudRepository<VoucherData, Long> {

    @Query("from VoucherData order by position asc")
    List<VoucherData> findAllOrderByPositionAsc();
}

