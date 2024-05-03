package com.project.impl.portfolio;

import com.project.helper.ErrorMessage;
import com.project.helper.IdHelper;
import com.project.model.Portfolio;
import com.project.model.request.PortfolioRequest;
import com.project.repository.PortfolioRepository;
import com.project.service.portfolio.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

  @Autowired
  private PortfolioRepository portfolioRepository;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Portfolio savePortfolio(PortfolioRequest portfolioRequest) throws Exception {
    Portfolio portfolio = this.generatePortfolio(portfolioRequest);
    portfolio.setId(idHelper.getNextSequenceId(Portfolio.COLLECTION_NAME));
    portfolio.setIsDeleted(0);
    return portfolioRepository.save(portfolio);
  }

  @Override
  public Portfolio updatePortfolio(Integer id, PortfolioRequest portfolioRequest) throws Exception {
    Portfolio portfolio = this.generatePortfolio(portfolioRequest);
    Query query = new Query(where("_id").is(id));
    Update update =
        new Update().set("image", portfolio.getImage()).set("gownName", portfolio.getGownName())
            .set("eventDate", portfolio.getEventDate()).set("venue", portfolio.getVenue())
            .set("wo", portfolio.getWo()).set("column", portfolio.getColumn())
            .set("name", portfolio.getName()).set("eventName", portfolio.getEventName());

    this.mongoTemplate.updateMulti(query, update, Portfolio.class);
    return this.portfolioRepository.findByIdAndIsDeleted(id, 0);
  }

  @Override
  public List<Portfolio> findAll() {
    return this.portfolioRepository.findByIsDeleted(0);
  }

  @Override
  public boolean deleteOutfit(Integer id) {
    Portfolio portfolio = this.portfolioRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(portfolio)) {
      return false;
    }
    this.deletePortfolioById(id);
    return true;
  }

  private Portfolio generatePortfolio(PortfolioRequest portfolioRequest) throws Exception {
    Portfolio portfolio = new Portfolio();
    portfolio.setImage(Optional.ofNullable(portfolioRequest.getImage())
        .orElseThrow(() -> new Exception(ErrorMessage.IMAGE_REQUIRED)));
    portfolio.setGownName(Optional.ofNullable(portfolioRequest.getGownName())
        .orElseThrow(() -> new Exception(ErrorMessage.GOWN_NAME_REQUIRED)));
    portfolio.setEventDate(Optional.ofNullable(portfolioRequest.getEventDate())
        .orElseThrow(() -> new Exception(ErrorMessage.EVENT_DATE_REQUIRED)));
    portfolio.setVenue(Optional.ofNullable(portfolioRequest.getVenue())
        .orElseThrow(() -> new Exception(ErrorMessage.VENUE_REQUIRED)));
    if (StringUtils.isNotBlank(portfolioRequest.getWo())) {
      portfolio.setWo(portfolioRequest.getWo());
    }
    portfolio.setColumn(Optional.ofNullable(portfolioRequest.getColumn())
        .orElseThrow(() -> new Exception(ErrorMessage.COLUMN_REQUIRED)));
    portfolio.setName(Optional.ofNullable(portfolioRequest.getName())
        .orElseThrow(() -> new Exception(ErrorMessage.NAME_REQUIRED)));
    portfolio.setEventName(Optional.ofNullable(portfolioRequest.getEventName())
        .orElseThrow(() -> new Exception(ErrorMessage.EVENT_NAME_REQUIRED)));
    return portfolio;
  }

  private void deletePortfolioById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, Portfolio.class);
  }
}
