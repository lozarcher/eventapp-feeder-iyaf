package com.loz.feeder.dao;

import com.loz.feeder.dao.model.CategoryData;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<CategoryData, Long> {
}

