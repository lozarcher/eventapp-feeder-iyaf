package com.loz.dao;


import com.loz.dao.model.GalleryData;
import com.loz.dao.model.InfoData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfoDao extends CrudRepository<InfoData, Long> {

    @Query("from InfoData order by title asc")
    List<InfoData> findAllOrderByTitle();

}

