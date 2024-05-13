package com.project.repository;

import com.project.model.Package;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PackageRepository extends MongoRepository<Package, String> {
  Package findByIdAndIsDeleted(Integer id, Integer isDeleted);
  List<Package> findByIsDeleted(Integer isDeleted);
}
