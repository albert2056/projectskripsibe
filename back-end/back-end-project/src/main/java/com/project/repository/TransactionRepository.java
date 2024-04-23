package com.project.repository;

import com.project.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

  Transaction findByIdAndIsDeleted(Integer id, Integer isDeleted);
}
