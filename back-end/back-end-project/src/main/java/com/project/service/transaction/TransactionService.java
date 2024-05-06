package com.project.service.transaction;

import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
  TransactionResponse book(BookRequest bookRequest) throws Exception;
  Transaction getInvoice(TransactionRequest transactionRequest) throws Exception;
  List<Transaction> getAllTransactions();
  Transaction changeStatus(String status);
}
