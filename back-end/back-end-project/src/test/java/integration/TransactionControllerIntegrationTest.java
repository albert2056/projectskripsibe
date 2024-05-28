package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.helper.ErrorMessage;
import com.project.helper.IdHelper;
import com.project.model.Package;
import com.project.model.Transaction;
import com.project.model.request.BookRequest;
import com.project.model.request.TransactionRequest;
import com.project.model.response.TransactionResponse;
import com.project.repository.PackageRepository;
import com.project.repository.TransactionRepository;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransactionControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private IdHelper idHelper;

  private BookRequest bookRequest;

  private TransactionRequest transactionRequest;

  private Package pack;

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

    pack = new Package();
    pack.setId(1);
    pack.setName("Premium");
    pack.setPrice(550000);
    pack.setIsDeleted(0);
    packageRepository.save(pack);
  }

  @AfterEach
  public void tearDown() {
    packageRepository.delete(pack);
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
  public void getInvoice_shouldReturnResponse() throws Exception {
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
    assertEquals("NOT PAID", transaction.getPaymentStatus());

    this.transactionRepository.delete(savedTransaction);
    this.idHelper.decrementSequenceId(Transaction.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void getAllTransactions_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        get(ProjectPath.TRANSACTION + ProjectPath.FIND_ALL).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    List<Transaction> transactions = getContent(result, new TypeReference<List<Transaction>>() {
    });

    assertNotNull(transactions);
  }

  @Positive
  @Test
  public void findByUserId_shouldReturnResponse() throws Exception {
    Transaction transaction = new Transaction();
    transaction.setId(100);
    transaction.setOutfitId(1);
    transaction.setEventId(1);
    transaction.setUserId(1);
    transaction.setPackageId(1);
    transaction.setName("Albert");
    transaction.setTotalUsher(8);
    transaction.setEventDate(new Date(9151462800000L));
    transaction.setVenue("Jakarta Event Hall");
    transaction.setWo("Akira");
    transaction.setUpdatedBy(1);
    transaction.setIsDeleted(0);

    this.transactionRepository.save(transaction);

    MvcResult result = mockMvc.perform(
        get(ProjectPath.TRANSACTION + ProjectPath.FIND_BY_USER_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("userId", transaction.getUserId().toString())).andReturn();

    List<Transaction> transactions = getContent(result, new TypeReference<List<Transaction>>() {
    });
    assertNotNull(transactions);

    this.transactionRepository.delete(transaction);
  }

  @Positive
  @Test
  public void findById_shouldReturnResponse() throws Exception {
    Transaction transaction = new Transaction();
    transaction.setId(100);
    transaction.setOutfitId(1);
    transaction.setEventId(1);
    transaction.setUserId(1);
    transaction.setPackageId(1);
    transaction.setName("Albert");
    transaction.setTotalUsher(8);
    transaction.setEventDate(new Date(9151462800000L));
    transaction.setVenue("Jakarta Event Hall");
    transaction.setWo("Akira");
    transaction.setUpdatedBy(1);
    transaction.setIsDeleted(0);

    this.transactionRepository.save(transaction);

    MvcResult result = mockMvc.perform(
        get(ProjectPath.TRANSACTION + ProjectPath.FIND_BY_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", transaction.getId().toString())).andReturn();

    Transaction transactionResult = getContent(result, new TypeReference<Transaction>() {
    });
    assertNotNull(transactionResult);

    this.transactionRepository.delete(transaction);
  }


  @Positive
  @Test
  public void changeStatus_shouldReturnResponse() throws Exception {
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

    mockMvc.perform(
        post(ProjectPath.TRANSACTION + ProjectPath.CHANGE_STATUS).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", savedTransaction.getId().toString())).andReturn();

    Transaction statusChangedTransaction =
        this.transactionRepository.findByIdAndIsDeleted(transaction.getId(),
            transaction.getIsDeleted());

    assertEquals("PAID", statusChangedTransaction.getPaymentStatus());

    this.transactionRepository.delete(statusChangedTransaction);
    this.idHelper.decrementSequenceId(Transaction.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void deleteTransaction_shouldReturnResponse() throws Exception {
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

    mockMvc.perform(
        delete(ProjectPath.TRANSACTION + ProjectPath.DELETE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", savedTransaction.getId().toString())).andReturn();

    Transaction deletedTransaction =
        this.transactionRepository.findByIdAndIsDeleted(transaction.getId(), 1);
    assertNotNull(deletedTransaction);

    this.transactionRepository.delete(deletedTransaction);
    this.idHelper.decrementSequenceId(Transaction.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void findUpcomingEvents_shouldReturnResponse() throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_YEAR, 6);
    Date weddinDdate = calendar.getTime();

    Transaction transaction = new Transaction();
    transaction.setId(100);
    transaction.setOutfitId(1);
    transaction.setEventId(1);
    transaction.setUserId(1);
    transaction.setPackageId(1);
    transaction.setName("Albert");
    transaction.setTotalUsher(8);
    transaction.setEventDate(weddinDdate);
    transaction.setVenue("Jakarta Event Hall");
    transaction.setWo("Akira");
    transaction.setUpdatedBy(1);
    transaction.setIsDeleted(0);

    this.transactionRepository.save(transaction);

    MvcResult result = mockMvc.perform(
        get(ProjectPath.TRANSACTION + ProjectPath.FIND_UPCOMING_EVENTS).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("threshold", "7")).andReturn();

    List<Transaction> transactions = getContent(result, new TypeReference<List<Transaction>>() {
    });
    assertNotNull(transactions);

    this.transactionRepository.delete(transaction);
  }
}
