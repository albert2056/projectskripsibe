package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.helper.IdHelper;
import com.project.helper.PortfolioHelper;
import com.project.model.Portfolio;
import com.project.model.request.PortfolioRequest;
import com.project.model.response.PortfolioResponse;
import com.project.service.portfolio.PortfolioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.PORTFOLIO)
@Tag(name = "PortfolioCrudController", description = "Portfolio CRUD Service API")
public class PortfolioCrudController {

  private final PortfolioService portfolioService;
  private final PortfolioHelper portfolioHelper;

  @PostMapping(ProjectPath.CREATE)
  public PortfolioResponse savePortfolio(@RequestBody PortfolioRequest portfolioRequest) throws Exception {
    try {
      return this.portfolioHelper.convertPortfolioToPortfolioResponse(this.portfolioService.savePortfolio(portfolioRequest));
    } catch (Exception e) {
      return this.portfolioHelper.convertToErrorPortfolioResponse(401, e.getMessage());
    }
  }

  @PostMapping(ProjectPath.UPDATE)
  public PortfolioResponse updatePortfolio(@RequestParam Integer id,
      @RequestBody PortfolioRequest portfolioRequest) throws Exception {
    try {
      return this.portfolioHelper.convertPortfolioToPortfolioResponse(
          this.portfolioService.updatePortfolio(id, portfolioRequest));
    } catch (Exception e) {
      return this.portfolioHelper.convertToErrorPortfolioResponse(401, e.getMessage());
    }
  }

  @GetMapping(ProjectPath.FIND_ALL)
  public List<PortfolioResponse> getAllPortfolios() {
    return this.portfolioHelper.convertPortfolioToPortfolioResponses(this.portfolioService.findAll());
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deletePortfolio(@RequestParam Integer id) throws Exception {
    return portfolioService.deletePortfolio(id);
  }

  @GetMapping(ProjectPath.FIND_BY_ID)
  public PortfolioResponse findPortfolioById(@RequestParam Integer id) {
    return this.portfolioHelper.convertPortfolioToPortfolioResponse(this.portfolioService.findById(id));
  }
}
