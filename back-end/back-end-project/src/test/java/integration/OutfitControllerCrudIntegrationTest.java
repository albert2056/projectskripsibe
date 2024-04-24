package integration;

import com.project.model.request.OutfitRequest;
import com.project.repository.OutfitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class OutfitControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private OutfitRepository outfitRepository;

  private OutfitRequest outfitRequest;

  @BeforeEach
  public void setUp() {
    outfitRequest = new OutfitRequest();
  }
}
