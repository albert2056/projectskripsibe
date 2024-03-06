package com.project.repository;

import com.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  User findById(Integer id);
  User findByEmail(String email);
}
