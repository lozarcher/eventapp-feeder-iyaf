package com.loz.feeder.dao;


import com.loz.feeder.dao.model.GalleryData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GalleryDao extends CrudRepository<GalleryData, Long> {

    @Query("from GalleryData where moderated=1 order by createdDate desc")
    List<GalleryData> findModeratedOrderByDate();

    @Query("from GalleryData order by createdDate desc")
    List<GalleryData> findAllOrderByDate();
}

