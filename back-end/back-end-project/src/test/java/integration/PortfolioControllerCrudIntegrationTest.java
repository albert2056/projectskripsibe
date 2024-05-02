package integration;

import com.project.model.request.PortfolioRequest;
import com.project.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioControllerCrudIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private PortfolioRepository portfolioRepository;

  private PortfolioRequest portfolioRequest;

  @BeforeEach
  public void setUp() {
    portfolioRequest = new PortfolioRequest();
  }


}
