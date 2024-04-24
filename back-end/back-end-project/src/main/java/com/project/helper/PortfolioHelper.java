package com.project.helper;

import com.project.model.Portfolio;
import com.project.model.response.PortfolioResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PortfolioHelper {

  public PortfolioResponse convertPortfolioToPortfolioResponse(Portfolio portfolio) {
    PortfolioResponse portfolioResponse = new PortfolioResponse();
    portfolioResponse.setId(portfolio.getId());
    portfolioResponse.setImage(portfolio.getImage());
    portfolioResponse.setGownName(portfolio.getGownName());
    portfolioResponse.setEventDate(portfolio.getEventDate());
    portfolioResponse.setVenue(portfolio.getVenue());
    portfolioResponse.setWo(portfolio.getWo());
    portfolioResponse.setColumn(portfolio.getColumn());
    portfolioResponse.setName(portfolio.getName());
    portfolioResponse.setEventName(portfolio.getEventName());
    portfolioResponse.setIsDeleted(portfolio.getIsDeleted());
    return portfolioResponse;
  }

  public PortfolioResponse convertToErrorPortfolioResponse(Integer code, String description) {
    PortfolioResponse portfolioResponse = new PortfolioResponse();
    portfolioResponse.setStatusCode(code);
    portfolioResponse.setDescription(description);
    return portfolioResponse;
  }

  public List<PortfolioResponse> convertPortfolioToPortfolioResponses(List<Portfolio> portfolios) {
    List<PortfolioResponse> portfolioResponses = new ArrayList<>();
    for (Portfolio portfolio : portfolios) {
      portfolioResponses.add(this.convertPortfolioToPortfolioResponse(portfolio));
    }
    return portfolioResponses;
  }
}
