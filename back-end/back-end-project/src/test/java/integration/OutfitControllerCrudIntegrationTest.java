package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.helper.IdHelper;
import com.project.model.Outfit;
import com.project.model.OutfitCategory;
import com.project.model.request.OutfitRequest;
import com.project.model.response.OutfitResponse;
import com.project.repository.OutfitCategoryRepository;
import com.project.repository.OutfitRepository;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class OutfitControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private OutfitRepository outfitRepository;

  @Autowired
  private OutfitCategoryRepository outfitCategoryRepository;

  @Autowired
  private IdHelper idHelper;

  private OutfitRequest outfitRequest;

  @BeforeEach
  public void setUp() {
    outfitRequest = new OutfitRequest();
    outfitRequest.setOutfitCategoryId(1);
    outfitRequest.setName("Albert");
    outfitRequest.setQty(10);
    outfitRequest.setImage("Albert.png");
    outfitRequest.setUpdatedBy(1);
  }

  @Positive
  @Test
  public void saveOutfit_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    assertNull(outfitResponse.getStatusCode());

    Outfit outfit = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 0);
    assertNotNull(outfit);

    outfitRepository.delete(outfit);
    this.idHelper.decrementSequenceId(Outfit.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void saveOutfit_emptyField_shouldReturnErrorResponse() throws Exception {
    outfitRequest.setName(null);

    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    assertNotNull(outfitResponse.getStatusCode());
    assertEquals("Outfit request fields cannot be null", outfitResponse.getDescription());
  }

  @Positive
  @Test
  public void saveOutfitCategory_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CATEGORY+ ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("name", "Albert")).andReturn();

    OutfitCategory outfitCategory = getContent(result, new TypeReference<OutfitCategory>() {
    });

    assertNotNull(outfitCategory);
    assertEquals("Albert", outfitCategory.getName());

    outfitCategoryRepository.delete(outfitCategory);
    this.idHelper.decrementSequenceId(OutfitCategory.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void updateOutfit_shouldReturnErrorResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    assertNull(outfitResponse.getStatusCode());

    Outfit outfit = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 0);
    assertNotNull(outfit);

    outfitRequest.setOutfitCategoryId(2);
    outfitRequest.setName("Albert2");
    outfitRequest.setQty(3);
    outfitRequest.setImage("Albert2.png");
    outfitRequest.setUpdatedBy(2);

    mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", outfit.getId().toString())
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    Outfit outfitUpdated = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 0);
    assertNotNull(outfitUpdated);

    assertEquals(2, outfitUpdated.getOutfitCategoryId());
    assertEquals("Albert2", outfitUpdated.getName());
    assertEquals(3, outfitUpdated.getQty());
    assertEquals("Albert2.png", outfitUpdated.getImage());
    assertEquals(2, outfitUpdated.getUpdatedBy());

    outfitRepository.delete(outfitUpdated);
    this.idHelper.decrementSequenceId(Outfit.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void updateOutfit_emptyField_shouldReturnErrorResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    assertNull(outfitResponse.getStatusCode());

    Outfit outfit = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 0);
    assertNotNull(outfit);

    outfitRequest.setOutfitCategoryId(2);
    outfitRequest.setName(null);
    outfitRequest.setQty(3);
    outfitRequest.setImage("Albert2.png");
    outfitRequest.setUpdatedBy(2);

    MvcResult result2 = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", outfit.getId().toString())
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse2 = getContent(result2, new TypeReference<OutfitResponse>() {
    });

    assertNotNull(outfitResponse2.getStatusCode());
    assertEquals("Outfit request fields cannot be null", outfitResponse2.getDescription());

    outfitRepository.delete(outfit);
    this.idHelper.decrementSequenceId(Outfit.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void getAllOutfits_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        get(ProjectPath.OUTFIT + ProjectPath.FIND_ALL).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)).andReturn();

    List<OutfitResponse> outfitResponses = getContent(result, new TypeReference<List<OutfitResponse>>() {
    });

    assertNotNull(outfitResponses);
  }

  @Positive
  @Test
  public void findOutfitsByOutfitCategoryId_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        get(ProjectPath.OUTFIT + ProjectPath.FIND_BY_OUTFIT_CATEGORY_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("outfitCategoryId", "1")).andReturn();

    List<OutfitResponse> outfitResponses = getContent(result, new TypeReference<List<OutfitResponse>>() {
    });

    assertNotNull(outfitResponses);
    assertEquals(5, outfitResponses.size());
  }

  @Positive
  @Test
  public void findOutfitById_shouldReturnResponse() throws Exception {
    Outfit outfit = new Outfit();
    outfit.setId(100);
    outfit.setOutfitCategoryId(outfitRequest.getOutfitCategoryId());
    outfit.setName(outfitRequest.getName());
    outfit.setIsDeleted(0);
    this.outfitRepository.save(outfit);

    MvcResult result = mockMvc.perform(
        get(ProjectPath.OUTFIT + ProjectPath.FIND_BY_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", "100")).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    assertNotNull(outfitResponse);
    assertEquals(outfitRequest.getName(), outfitResponse.getName());
    this.outfitRepository.delete(outfit);
  }

  @Positive
  @Test
  public void deleteOutfit_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.OUTFIT + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(outfitRequest))).andReturn();

    OutfitResponse outfitResponse = getContent(result, new TypeReference<OutfitResponse>() {
    });

    Outfit outfit = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 0);
    assertNotNull(outfit);

    mockMvc.perform(
        delete(ProjectPath.OUTFIT + ProjectPath.DELETE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", outfit.getId().toString())).andReturn();

    Outfit outfitDeleted = this.outfitRepository.findByIdAndIsDeleted(outfitResponse.getId(), 1);
    assertNotNull(outfitDeleted);

    outfitRepository.delete(outfit);
    this.idHelper.decrementSequenceId(Outfit.COLLECTION_NAME);
  }

}
