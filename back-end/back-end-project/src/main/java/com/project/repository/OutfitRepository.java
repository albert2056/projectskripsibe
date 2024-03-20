package com.project.repository;

import com.project.model.Outfit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutfitRepository extends MongoRepository<Outfit, String> {
  Outfit findByIdAndIsDeletedFalse(Integer id);
  List<Outfit> findByIsDeletedFalse();
}
