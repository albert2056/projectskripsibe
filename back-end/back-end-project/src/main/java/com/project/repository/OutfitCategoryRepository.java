package com.project.repository;

import com.project.model.OutfitCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutfitCategoryRepository extends MongoRepository<OutfitCategory, String> {
  OutfitCategory findById(Integer id);
}
