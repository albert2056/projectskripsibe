package com.project.repository;

import com.project.model.Outfit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OutfitRepository extends MongoRepository<Outfit, String> {
  Outfit findById(Integer id);
}
