package com.loz.dao;


import com.loz.dao.model.EventData;
import com.loz.dao.model.GalleryData;
import com.loz.dao.model.VenueData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface GalleryDao extends CrudRepository<GalleryData, Long> {

    @Query("from GalleryData order by createdDate desc")
    List<GalleryData> findAllOrderByDate();
}

