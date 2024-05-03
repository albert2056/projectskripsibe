package com.project.repository;

import com.project.model.Package;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageRepository extends MongoRepository<Package, String> {
  Package findByIdAndIsDeleted(Integer id, Integer isDeleted);
}
