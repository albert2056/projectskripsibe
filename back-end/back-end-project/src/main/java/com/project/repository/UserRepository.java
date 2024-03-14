package com.project.repository;

import com.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
  User findByIdAndIsDeletedFalse(Integer id);
  User findByEmailAndIsDeletedFalse(String email);
  List<User> findByIsDeletedFalse();
}
