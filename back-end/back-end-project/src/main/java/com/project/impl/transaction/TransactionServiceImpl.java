package com.project.impl.transaction;

import com.project.helper.ErrorMessage;
import com.project.helper.IdHelper;
import com.project.model.Outfit;
import com.project.model.Package;
import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;
import com.project.repository.PackageRepository;
import com.project.repository.TransactionRepository;
import com.project.service.transaction.TransactionService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public TransactionResponse book(BookRequest bookRequest) throws Exception {
    this.validateBookRequest(bookRequest);
    return this.constructResponse(bookRequest);
  }

  @Override
  public Transaction getInvoice(TransactionRequest transactionRequest) throws Exception {
    return this.constructTransaction(transactionRequest);
  }

  @Override
  public List<Transaction> getAllTransactions() {
    return this.transactionRepository.findByIsDeleted(0);
  }

  @Override
  public Transaction changeStatus(Integer id) {
    Transaction transaction = this.transactionRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(transaction)) {
      return null;
    }
    return this.updateStatus(id);
  }

  @Override
  public boolean deleteTransaction(Integer id) {
    Transaction transaction = this.transactionRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(transaction)) {
      return false;
    }
    this.deleteTransactionById(id);
    return true;
  }

  private Transaction updateStatus(Integer id) {
    Query query = new Query(where("_id").is(id));
    Update update = new Update().set("paymentStatus", "PAID");

    this.mongoTemplate.updateMulti(query, update, Transaction.class);
    return this.transactionRepository.findByIdAndIsDeleted(id, 0);
  }

  private void deleteTransactionById(Integer id) {
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, Transaction.class);
  }

  private void validateBookRequest(BookRequest bookRequest) throws Exception {
    this.validateNameAndVenue(bookRequest);
    this.validateTotalUsher(bookRequest.getTotalUsher());
    this.validateEventDate(bookRequest.getEventDate());
  }

  private void validateTotalUsher(Integer totalUsher) throws Exception {
    if (totalUsher == null || totalUsher <= 0) {
      throw new Exception(ErrorMessage.TOTAL_USHER);
    }
  }

  private void validateEventDate(Date eventDate) throws Exception {
    if (eventDate == null) {
      throw new Exception(ErrorMessage.EVENT_DATE_NULL);
    }

    Date now = new Date();
    if (eventDate.before(now)) {
      throw new Exception(ErrorMessage.EVENT_DATE_PAST);
    }
  }

  private void validateNameAndVenue(BookRequest bookRequest) throws Exception {
    if (StringUtils.isEmpty(bookRequest.getName())) {
      throw new Exception(ErrorMessage.NAME_EMPTY);
    }

    if (StringUtils.isEmpty(bookRequest.getVenue())) {
      throw new Exception(ErrorMessage.VENUE_EMPTY);
    }
  }

  private TransactionResponse constructResponse(BookRequest bookRequest) {
    TransactionResponse transactionResponse = new TransactionResponse();
    transactionResponse.setName(bookRequest.getName());
    transactionResponse.setTotalUsher(bookRequest.getTotalUsher());
    transactionResponse.setEventDate(bookRequest.getEventDate());
    transactionResponse.setVenue(bookRequest.getVenue());
    if (StringUtils.isNotEmpty(bookRequest.getWo())) {
      transactionResponse.setWo(bookRequest.getWo());
    }
    return transactionResponse;
  }

  private Transaction constructTransaction(TransactionRequest transactionRequest) {
    Transaction transaction = new Transaction();
    transaction.setId(idHelper.getNextSequenceId(Transaction.COLLECTION_NAME));
    transaction.setOutfitId(transactionRequest.getOutfitId());
    transaction.setEventId(transactionRequest.getEventId());
    transaction.setUserId(transactionRequest.getUserId());
    transaction.setPackageId(transactionRequest.getPackageId());
    transaction.setName(transactionRequest.getName());
    transaction.setTotalUsher(transactionRequest.getTotalUsher());
    transaction.setEventDate(transactionRequest.getEventDate());
    transaction.setVenue(transactionRequest.getVenue());
    if (StringUtils.isNotEmpty(transactionRequest.getWo())) {
      transaction.setWo(transactionRequest.getWo());
    }
    transaction.setTotalPrice(
        this.getTotalPrice(transactionRequest.getPackageId(), transactionRequest.getTotalUsher()));
    transaction.setPaymentStatus("NOT PAID");
    transaction.setUpdatedBy(transactionRequest.getUpdatedBy());
    transaction.setCreatedDate(new Date());
    transaction.setUpdatedDate(new Date());
    transaction.setIsDeleted(0);
    return transactionRepository.save(transaction);
  }

  private Integer getTotalPrice(Integer packageId, Integer totalUsher) {
//    Package pack = this.packageRepository.findByIdAndIsDeleted(packageId, 0);
//    return totalUsher * pack.getPrice();
    if (packageId == 1) {
      return totalUsher * 550000;
    } else {
      return 400000;
    }
  }
}
