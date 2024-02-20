package com.project.repository;

import com.project.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
  Product findByName(String name);
}
