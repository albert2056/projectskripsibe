package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.helper.TransactionHelper;
import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;
import com.project.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  @PostMapping(ProjectPath.INVOICE)
  public Transaction getInvoice(@RequestBody TransactionRequest transactionRequest) {
    return this.transactionService.getInvoice(transactionRequest);
  }

  @GetMapping(ProjectPath.FIND_ALL)
  public List<Transaction> getAllTransactions() {
    return this.transactionService.findAllTransactions();
  }

  @GetMapping(ProjectPath.FIND_BY_USER_ID)
  public List<Transaction> findTransactionsByUserId(@RequestParam Integer userId) {
    return this.transactionService.findTransactionsByUserId(userId);
  }

  @GetMapping(ProjectPath.FIND_BY_ID)
  public Transaction findTransactionById(@RequestParam Integer id) {
    return this.transactionService.findTransactionsById(id);
  }

  @PostMapping(ProjectPath.CHANGE_STATUS)
  public Transaction changeStatus(@RequestParam Integer id) {
    return this.transactionService.changeStatus(id);
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deleteTransaction(@RequestParam Integer id) {
    return this.transactionService.deleteTransaction(id);
  }

  @GetMapping(ProjectPath.FIND_UPCOMING_EVENTS)
  public List<Transaction> findUpcomingEvents(@RequestParam Integer threshold) {
    return this.transactionService.findUpcomingEvents(threshold);
  }

}
