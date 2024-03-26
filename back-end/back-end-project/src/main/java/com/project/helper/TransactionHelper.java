package com.project.helper;

import com.project.model.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionHelper {

  public TransactionResponse convertToErrorTransactionResponse(Integer code, String description) {
    TransactionResponse transactionRespose = new TransactionResponse();
    transactionRespose.setStatusCode(code);
    transactionRespose.setDescription(description);
    return transactionRespose;
  }
}
