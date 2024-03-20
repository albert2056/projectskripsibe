package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.helper.ErrorMessage;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.MvcResult;

public class UserControllerCrudIntegrationTest extends BaseIntegrationTest{

  @Autowired
  private UserRepository userRepository;

  private UserRequest userRequest;

  @BeforeEach
  public void setUp() {
    userRequest = new UserRequest();
    userRequest.setRoleId(1);
    userRequest.setName("name");
    userRequest.setPhoneNumber("12345678");
    userRequest.setEmail("albert@gmail.com");
    userRequest.setPassword("Albert1234");
  }

  @Positive
  @Test
  public void createUser_success_shouldReturnResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.USER + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userRequest))).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertNull(userResponse.getStatusCode());

    User user = this.userRepository.findByIdAndIsDeleted(userResponse.getId(), 0);
    assertNotNull(user);
  }

  @Negative
  @Test
  public void createUser_passwordNotContainUpperCase_shouldReturnErrorResponse() throws Exception {
    userRequest.setPassword("albert1234");

    MvcResult result = mockMvc.perform(
        post(ProjectPath.USER + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userRequest))).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertEquals(401, userResponse.getStatusCode());
    assertEquals(ErrorMessage.PASSWORD_UPPERCASE, userResponse.getDescription());
  }

  @Negative
  @Test
  public void createUser_passwordNotContainLowerCase_shouldReturnErrorResponse() throws Exception {
    userRequest.setPassword("ALBERT1234");

    MvcResult result = mockMvc.perform(
        post(ProjectPath.USER + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userRequest))).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertEquals(401, userResponse.getStatusCode());
    assertEquals(ErrorMessage.PASSWORD_LOWERCASE, userResponse.getDescription());
  }

  @Negative
  @Test
  public void createUser_passwordNotContainNumericDigit_shouldReturnErrorResponse() throws Exception {
    userRequest.setPassword("albertALBERT");

    MvcResult result = mockMvc.perform(
        post(ProjectPath.USER + ProjectPath.CREATE).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userRequest))).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertEquals(401, userResponse.getStatusCode());
    assertEquals(ErrorMessage.PASSWORD_NUMBER, userResponse.getDescription());
  }

}
