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

  @Positive
  @Test
  public void savePortfolio_emptyField_shouldReturnResponse() throws Exception {
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
  public void getPortfolio_shouldReturnResponse() throws Exception {
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
}
