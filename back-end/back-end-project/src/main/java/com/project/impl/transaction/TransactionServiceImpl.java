package com.project.impl.transaction;

import com.project.helper.ErrorMessage;
import com.project.model.request.BookRequest;
import com.project.model.response.TransactionResponse;
import com.project.service.transaction.TransactionService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Override
  public TransactionResponse book(BookRequest bookRequest) throws Exception {
    this.validateBookRequest(bookRequest);
    return this.constructResponse(bookRequest);
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
}
