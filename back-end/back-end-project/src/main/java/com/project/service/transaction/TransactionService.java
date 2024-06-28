package com.project.service.transaction;

import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
  TransactionResponse book(BookRequest bookRequest) throws Exception;
  Transaction getInvoice(TransactionRequest transactionRequest);
  List<Transaction> findAllTransactions();
  List<Transaction> findTransactionsByUserId(Integer userId);
  List<Transaction> findUpcomingEvents(Integer threshold);
  Transaction findTransactionsById(Integer id);
  Transaction changeStatus(Integer id);
  boolean deleteTransaction(Integer id);
}
