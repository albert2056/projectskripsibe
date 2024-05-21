package com.project.repository;

import com.project.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

  Transaction findByIdAndIsDeleted(Integer id, Integer isDeleted);
  List<Transaction> findByIsDeleted(Integer isDeleted);
  List<Transaction> findByUserIdAndIsDeleted(Integer id, Integer isDeleted);
  @Query("{ 'eventDate': { $gte: ?0, $lte: ?1 }, 'isDeleted': ?2 }")
  List<Transaction> findByEventDateRangeAndIsDeleted(Date startDate, Date endDate, Integer isDeleted);
}
