package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.helper.TransactionHelper;
import com.project.model.request.BookRequest;
import com.project.model.response.TransactionResponse;
import com.project.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.TRANSACTION)
@Tag(name = "TransactionController", description = "Transaction Service API")
public class TransactionController {

  private final TransactionService transactionService;
  private final TransactionHelper transactionHelper;

  @PostMapping(ProjectPath.BOOK)
  public TransactionResponse book(@RequestBody BookRequest bookRequest) throws Exception {
    try {
      return this.transactionService.book(bookRequest);
    } catch (Exception e) {
      return this.transactionHelper.convertToErrorTransactionResponse(401, e.getMessage());
    }
  }
}
