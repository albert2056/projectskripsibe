package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.helper.ErrorMessage;
import com.project.helper.IdHelper;
import com.project.model.Portfolio;
import com.project.model.request.PortfolioRequest;
import com.project.model.response.PortfolioResponse;
import com.project.repository.PortfolioRepository;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PortfolioControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private PortfolioRepository portfolioRepository;

  @Autowired
  private IdHelper idHelper;

  private PortfolioRequest portfolioRequest;

  @BeforeEach
  public void setUp() {
    portfolioRequest = new PortfolioRequest();
    portfolioRequest.setImage("Albert.png");
    portfolioRequest.setGownName("Elegant Gown");
    portfolioRequest.setEventDate(new Date());
    portfolioRequest.setVenue("Grand Hall");
    portfolioRequest.setWo("Albert");
    portfolioRequest.setColumn(1);
    portfolioRequest.setName("Wedding");
    portfolioRequest.setEventName("Wedding Event");
  }

  @Positive
  @Test
  public void savePortfolio_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNull(portfolioResponse.getStatusCode());

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    portfolioRepository.delete(portfolio);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void savePortfolio_woNull_shouldReturnResponse() throws Exception {
    portfolioRequest.setWo(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNull(portfolioResponse.getStatusCode());

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    portfolioRepository.delete(portfolio);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Negative
  @Test
  public void savePortfolio_emptyField_shouldReturnErrorResponse() throws Exception {
    portfolioRequest.setImage(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNotNull(portfolioResponse.getStatusCode());
    assertEquals(ErrorMessage.IMAGE_REQUIRED, portfolioResponse.getDescription());
  }

  @Positive
  @Test
  public void updatePortfolio_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNull(portfolioResponse.getStatusCode());

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    portfolioRequest.setImage("Albert2.png");
    portfolioRequest.setGownName("Elegant Gown2");
    portfolioRequest.setVenue("Grand Hall2");
    portfolioRequest.setWo("Albert2");
    portfolioRequest.setColumn(2);
    portfolioRequest.setName("Wedding2");
    portfolioRequest.setEventName("Wedding Event2");

    mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", portfolio.getId().toString())
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    Portfolio portfolioUpdated = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolioUpdated);

    assertEquals("Albert2.png", portfolioUpdated.getImage());
    assertEquals("Elegant Gown2", portfolioUpdated.getGownName());
    assertEquals("Grand Hall2", portfolioUpdated.getVenue());
    assertEquals("Albert2", portfolioUpdated.getWo());
    assertEquals(2, portfolioUpdated.getColumn());
    assertEquals("Wedding2", portfolioUpdated.getName());
    assertEquals("Wedding Event2", portfolioUpdated.getEventName());

    portfolioRepository.delete(portfolioUpdated);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void updatePortfolio_woNull_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNull(portfolioResponse.getStatusCode());

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    portfolioRequest.setImage("Albert2.png");
    portfolioRequest.setGownName("Elegant Gown2");
    portfolioRequest.setVenue("Grand Hall2");
    portfolioRequest.setColumn(2);
    portfolioRequest.setName("Wedding2");
    portfolioRequest.setEventName("Wedding Event2");

    mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", portfolio.getId().toString())
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    Portfolio portfolioUpdated = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolioUpdated);

    assertEquals("Albert2.png", portfolioUpdated.getImage());
    assertEquals("Elegant Gown2", portfolioUpdated.getGownName());
    assertEquals("Grand Hall2", portfolioUpdated.getVenue());
    assertEquals(2, portfolioUpdated.getColumn());
    assertEquals("Wedding2", portfolioUpdated.getName());
    assertEquals("Wedding Event2", portfolioUpdated.getEventName());

    portfolioRepository.delete(portfolioUpdated);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Negative
  @Test
  public void updatePortfolio_emptyField_shouldReturnErrorResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    assertNull(portfolioResponse.getStatusCode());

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    portfolioRequest.setImage(null);
    portfolioRequest.setGownName("Elegant Gown2");
    portfolioRequest.setVenue("Grand Hall2");
    portfolioRequest.setWo("Albert2");
    portfolioRequest.setColumn(2);
    portfolioRequest.setName("Wedding2");
    portfolioRequest.setEventName("Wedding Event2");

    MvcResult result2 = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", portfolio.getId().toString())
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse2 = getContent(result2, new TypeReference<PortfolioResponse>() {
    });

    assertNotNull(portfolioResponse2.getStatusCode());
    assertEquals(ErrorMessage.IMAGE_REQUIRED, portfolioResponse2.getDescription());

    portfolioRepository.delete(portfolio);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void getAllPortfolios_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        get(ProjectPath.PORTFOLIO + ProjectPath.FIND_ALL).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    List<PortfolioResponse> portfolioResponses = getContent(result, new TypeReference<List<PortfolioResponse>>() {
    });

    assertNotNull(portfolioResponses);
  }

  @Positive
  @Test
  public void deletePortfolio_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 0);
    assertNotNull(portfolio);

    mockMvc.perform(
        delete(ProjectPath.PORTFOLIO + ProjectPath.DELETE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", portfolio.getId().toString())).andReturn();

    Portfolio portfolioDeleted = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponse.getId(), 1);
    assertNotNull(portfolioDeleted);

    portfolioRepository.delete(portfolio);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void findPortfolioById_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PORTFOLIO + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(portfolioRequest))).andReturn();

    PortfolioResponse portfolioResponse = getContent(result, new TypeReference<PortfolioResponse>() {
    });

    MvcResult result2 = mockMvc.perform(
        get(ProjectPath.PORTFOLIO + ProjectPath.FIND_BY_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .param("id", portfolioResponse.getId().toString())
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    PortfolioResponse portfolioResponses = getContent(result2, new TypeReference<PortfolioResponse>() {
    });

    assertNotNull(portfolioResponses);

    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(portfolioResponses.getId(), 0);
    assertNotNull(portfolio);

    portfolioRepository.delete(portfolio);
    this.idHelper.decrementSequenceId(Portfolio.COLLECTION_NAME);
  }
}
