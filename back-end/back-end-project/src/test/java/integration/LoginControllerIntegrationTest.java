package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.controller.path.ProjectPath;
import com.project.helper.ErrorMessage;
import com.project.helper.UserHelper;
import com.project.model.User;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class LoginControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserHelper userHelper;

  @BeforeEach
  public void setUp() {
    userRepository.deleteAll();
  }

  @Positive
  @Test
  public void login_shouldReturnResponse() throws Exception {
    User user = this.createUser();

    MvcResult result = mockMvc.perform(
        post(ProjectPath.LOGIN).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("email", user.getEmail())
            .param("password", "Albert1234")).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertNull(userResponse.getStatusCode());
  }

  @Negative
  @Test
  public void login_emailDoesNotExist_shouldReturnErrorResponse() throws Exception {
    MvcResult result = mockMvc.perform(
        post(ProjectPath.LOGIN).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("email", "invalid")
            .param("password", "invalid")).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertNotNull(userResponse.getStatusCode());
    assertEquals(ErrorMessage.USER_NOT_FOUND, userResponse.getDescription());
  }

  @Negative
  @Test
  public void login_passwordNotMatch_shouldReturnErrorResponse() throws Exception {
    User user = this.createUser();

    MvcResult result = mockMvc.perform(
        post(ProjectPath.LOGIN).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("email", user.getEmail())
            .param("password", "invalid")).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertNotNull(userResponse.getStatusCode());
    assertEquals(ErrorMessage.LOGIN_NOT_MATCH, userResponse.getDescription());
  }

  @Negative
  @Test
  public void login_withDeletedUser_shouldReturnErrorResponse() throws Exception {
    User user = this.createDeletedUser();

    MvcResult result = mockMvc.perform(
        post(ProjectPath.LOGIN).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON).param("email", user.getEmail())
            .param("password", user.getPassword())).andReturn();
    UserResponse userResponse = getContent(result, new TypeReference<UserResponse>() {
    });

    assertNotNull(userResponse.getStatusCode());
    assertEquals(ErrorMessage.USER_NOT_FOUND, userResponse.getDescription());
  }

  private User createUser(){
    User user = new User();
    user.setId(1);
    user.setRoleId(1);
    user.setName("name");
    user.setPhoneNumber("1234567890");
    user.setEmail("albert@gmail.com");
    user.setPassword(this.userHelper.encodePassword("Albert1234"));
    user.setIsDeleted(0);
    return this.userRepository.save(user);
  }

  private User createDeletedUser(){
    User user = new User();
    user.setId(1);
    user.setRoleId(1);
    user.setName("name");
    user.setPhoneNumber("1234567890");
    user.setEmail("albert@gmail.com");
    user.setPassword(this.userHelper.encodePassword("Albert1234"));
    user.setIsDeleted(1);
    return this.userRepository.save(user);
  }
}
