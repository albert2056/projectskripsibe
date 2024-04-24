package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.helper.ErrorMessage;
import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;
import com.project.repository.TransactionRepository;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private TransactionRepository transactionRepository;

  private BookRequest bookRequest;

  private TransactionRequest transactionRequest;

  @BeforeEach
  public void setUp() {
    bookRequest = new BookRequest();
    bookRequest.setName("Albert");
    bookRequest.setTotalUsher(10);
    bookRequest.setVenue("Jakarta");
    bookRequest.setEventDate(new Date(9151462800000L));
    bookRequest.setWo("Akira");

    transactionRequest = new TransactionRequest();
    transactionRequest.setOutfitId(1);
    transactionRequest.setEventId(1);
    transactionRequest.setUserId(1);
    transactionRequest.setPackageId(1);
    transactionRequest.setName("Albert");
    transactionRequest.setTotalUsher(8);
    transactionRequest.setEventDate(new Date(9151462800000L));
    transactionRequest.setVenue("Jakarta Event Hall");
    transactionRequest.setWo("Akira");
    transactionRequest.setUpdatedBy(1);
  }

  @Positive
  @Test
  public void book_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNull(transactionResponse.getStatusCode());
  }

  @Positive
  @Test
  public void book_woNull_shouldReturnResponse() throws Exception {
    bookRequest.setWo(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNull(transactionResponse.getStatusCode());
  }

  @Negative
  @Test
  public void book_nameNull_shouldReturnErrorResponse() throws Exception {
    bookRequest.setName(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNotNull(transactionResponse.getStatusCode());
    assertEquals(ErrorMessage.NAME_EMPTY, transactionResponse.getDescription());
  }

  @Negative
  @Test
  public void book_venueNull_shouldReturnErrorResponse() throws Exception {
    bookRequest.setVenue(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNotNull(transactionResponse.getStatusCode());
    assertEquals(ErrorMessage.VENUE_EMPTY, transactionResponse.getDescription());
  }

  @Negative
  @Test
  public void book_totalUsherNull_shouldReturnErrorResponse() throws Exception {
    bookRequest.setTotalUsher(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNotNull(transactionResponse.getStatusCode());
    assertEquals(ErrorMessage.TOTAL_USHER, transactionResponse.getDescription());
  }

  @Negative
  @Test
  public void book_eventDateNull_shouldReturnErrorResponse() throws Exception {
    bookRequest.setEventDate(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNotNull(transactionResponse.getStatusCode());
    assertEquals(ErrorMessage.EVENT_DATE_NULL, transactionResponse.getDescription());
  }

  @Negative
  @Test
  public void book_eventDateBeforeNow_shouldReturnErrorResponse() throws Exception {
    bookRequest.setEventDate(new Date(1640970000000L));

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.BOOK).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bookRequest))).andReturn();

    TransactionResponse transactionResponse = getContent(result, new TypeReference<TransactionResponse>() {
    });

    assertNotNull(transactionResponse.getStatusCode());
    assertEquals(ErrorMessage.EVENT_DATE_PAST, transactionResponse.getDescription());
  }

  @Positive
  @Test
  public void getInvoice_premium_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.INVOICE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(transactionRequest))).andReturn();

    Transaction transaction = getContent(result, new TypeReference<Transaction>() {
    });

    Transaction savedTransaction =
        this.transactionRepository.findByIdAndIsDeleted(transaction.getId(),
            transaction.getIsDeleted());

    assertNotNull(savedTransaction);
    assertEquals(savedTransaction, transaction);
    assertEquals("WAITING FOR PAYMENT", transaction.getPaymentStatus());
    assertEquals(4400000, transaction.getTotalPrice());
  }

  @Positive
  @Test
  public void getInvoice_standard_shouldReturnResponse() throws Exception {
    transactionRequest.setPackageId(2);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.INVOICE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(transactionRequest))).andReturn();

    Transaction transaction = getContent(result, new TypeReference<Transaction>() {
    });

    Transaction savedTransaction =
        this.transactionRepository.findByIdAndIsDeleted(transaction.getId(),
            transaction.getIsDeleted());

    assertNotNull(savedTransaction);
    assertEquals(savedTransaction, transaction);
    assertEquals("WAITING FOR PAYMENT", transaction.getPaymentStatus());
    assertEquals(3200000, transaction.getTotalPrice());
  }
}
