package com.project.service.portfolio;

import com.project.model.Portfolio;
import com.project.model.request.PortfolioRequest;

import java.util.List;

public interface PortfolioService {
  Portfolio savePortfolio(PortfolioRequest portfolioRequest) throws Exception;
  Portfolio updatePortfolio(Integer id, PortfolioRequest portfolioRequest) throws Exception;
  List<Portfolio> findAll();
  boolean deletePortfolio(Integer id);
  Portfolio findById(Integer id);
}
