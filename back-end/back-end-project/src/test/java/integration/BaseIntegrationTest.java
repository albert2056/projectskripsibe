package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.application.ProjectSkripsiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = ProjectSkripsiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = {"/application.properties"})
public class BaseIntegrationTest {
  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  public <T> T getContent(MvcResult result, TypeReference<?> typeReference) throws Exception {
    return (T) objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
  }
}
