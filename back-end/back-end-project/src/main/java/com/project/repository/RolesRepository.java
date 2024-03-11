package com.project.repository;

import com.project.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolesRepository extends MongoRepository<Roles, String> {
  Roles findById(Integer id);
}
