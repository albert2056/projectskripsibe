package com.project.repository;

import com.project.model.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
  Portfolio findByIdAndIsDeleted(Integer id, Integer isDeleted);
  List<Portfolio> findByIsDeleted(Integer isDeleted);
}
