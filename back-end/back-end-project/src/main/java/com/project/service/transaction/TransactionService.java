package com.project.service.transaction;

import com.project.model.request.BookRequest;
import com.project.model.response.TransactionResponse;

public interface TransactionService {
  TransactionResponse book(BookRequest bookRequest) throws Exception;
}
