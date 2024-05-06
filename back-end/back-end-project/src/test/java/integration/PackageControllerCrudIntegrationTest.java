package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.controller.path.ProjectPath;
import com.project.helper.IdHelper;
import com.project.model.Package;
import com.project.repository.PackageRepository;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class PackageControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private IdHelper idHelper;

  @Positive
  @Test
  public void savePackage_shouldReturnRsponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PACKAGE + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("name", "Standard").param("price",
                String.valueOf(550000))).andReturn();

    Package pack = getContent(result, new TypeReference<Package>() {
    });

    Package packResult = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packResult);

    packageRepository.delete(packResult);
    this.idHelper.decrementSequenceId(Package.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void updatePackage_shouldReturnRsponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PACKAGE + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("name", "premium").param("price",
                String.valueOf(550000))).andReturn();

    Package pack = getContent(result, new TypeReference<Package>() {
    });

    Package packResult = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packResult);

    mockMvc.perform(
        post(ProjectPath.PACKAGE + ProjectPath.UPDATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", String.valueOf(packResult.getId()))
            .param("name", "standard").param("price", String.valueOf(400000))).andReturn();

    Package packUpdate = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packUpdate);

    assertEquals("standard", packUpdate.getName());
    assertEquals(400000, packUpdate.getPrice());

    packageRepository.delete(packUpdate);
    this.idHelper.decrementSequenceId(Package.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void findById_shouldReturnRsponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PACKAGE + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("name", "premium").param("price",
                String.valueOf(550000))).andReturn();

    Package pack = getContent(result, new TypeReference<Package>() {
    });

    Package packResult = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packResult);

    mockMvc.perform(
        get(ProjectPath.PACKAGE + ProjectPath.FIND_BY_ID).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", packResult.getId().toString())).andReturn();

    Package packUpdate = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packUpdate);

    packageRepository.delete(packUpdate);
    this.idHelper.decrementSequenceId(Package.COLLECTION_NAME);
  }

  @Positive
  @Test
  public void deletePackage_shouldReturnRsponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.PACKAGE + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("name", "Standard").param("price",
                String.valueOf(550000))).andReturn();

    Package pack = getContent(result, new TypeReference<Package>() {
    });

    Package packResult = this.packageRepository.findByIdAndIsDeleted(pack.getId(), 0);
    assertNotNull(packResult);

    mockMvc.perform(
        delete(ProjectPath.PACKAGE + ProjectPath.DELETE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("id", packResult.getId().toString())).andReturn();

    Package packDeleted = this.packageRepository.findByIdAndIsDeleted(packResult.getId(), 1);
    assertNotNull(packDeleted);

    packageRepository.delete(packDeleted);
    this.idHelper.decrementSequenceId(Package.COLLECTION_NAME);
  }
}
