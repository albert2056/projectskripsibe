package com.project.repository;

import com.project.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

  Transaction findByIdAndIsDeleted(Integer id, Integer isDeleted);
  List<Transaction> findByIsDeleted(Integer isDeleted);
}
