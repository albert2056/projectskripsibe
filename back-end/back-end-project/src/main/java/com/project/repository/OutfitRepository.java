package com.project.repository;

import com.project.model.Outfit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OutfitRepository extends MongoRepository<Outfit, String> {
  Outfit findByIdAndIsDeleted(Integer id, Integer isDeleted);
  List<Outfit> findByIsDeleted(Integer isDeleted, Sort sort);
  List<Outfit> findByOutfitCategoryIdAndIsDeleted(Integer outfitCategoryId, Integer isDeleted, Sort sort);
}
