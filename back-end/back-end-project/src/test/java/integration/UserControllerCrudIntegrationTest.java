package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.path.ProjectPath;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

public class UserControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  private UserRequest userRequest;

  @BeforeEach
  public void setUp() {
    userRequest = new UserRequest();
    userRequest.setRoleId(1);
    userRequest.setName("name");
    userRequest.setPhoneNumber("1234567890");
    userRequest.setEmail("albert@gmail.com");
    userRequest.setPassword("Albert1234");
  }

  @Positive
  @Test
  public void findUser_shouldReturnResponse() throws Exception {
    User user = new User();
    user.setId(1);
    user.setRoleId(userRequest.getRoleId());
    user.setEmail(userRequest.getEmail());
    user.setName(userRequest.getName());
    user.setPassword(userRequest.getPassword());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    user.setIsDeleted(0);
    userRepository.save(user);

    MvcResult result = mockMvc.perform(
        get(ProjectPath.USER + ProjectPath.FIND_ALL).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userRequest))).andReturn();
    List<UserResponse> userResponse = getContent(result, new TypeReference<List<UserResponse>>() {
    });

    assertNotNull(userResponse);
  }

}
