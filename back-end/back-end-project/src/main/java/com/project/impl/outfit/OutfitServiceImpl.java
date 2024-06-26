package com.project.impl.outfit;

import com.project.helper.IdHelper;
import com.project.model.Outfit;
import com.project.model.OutfitCategory;
import com.project.model.request.OutfitRequest;
import com.project.repository.OutfitCategoryRepository;
import com.project.repository.OutfitRepository;
import com.project.service.outift.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class OutfitServiceImpl implements OutfitService {

  @Autowired
  private OutfitRepository outfitRepository;

  @Autowired
  private OutfitCategoryRepository outfitCategoryRepository;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Outfit saveOutfit(OutfitRequest outfitRequest) throws Exception {
    this.validateFields(outfitRequest);
    Outfit outfit = new Outfit();
    outfit.setId(idHelper.getNextSequenceId(Outfit.COLLECTION_NAME));
    outfit.setOutfitCategoryId(outfitRequest.getOutfitCategoryId());
    outfit.setName(outfitRequest.getName());
    outfit.setQty(outfitRequest.getQty());
    outfit.setImage(outfitRequest.getImage());
    outfit.setUpdatedBy(outfitRequest.getUpdatedBy());
    outfit.setCreatedDate(new Date());
    outfit.setUpdatedDate(new Date());
    outfit.setIsDeleted(0);
    return outfitRepository.save(outfit);
  }

  @Override
  public Outfit updateOutfit(Integer id, OutfitRequest outfitRequest) throws Exception {
    this.validateFields(outfitRequest);
    Query query = new Query(where("_id").is(id));
    Update update = new Update().set("outfitCategoryId", outfitRequest.getOutfitCategoryId())
        .set("name", outfitRequest.getName()).set("qty", outfitRequest.getQty())
        .set("image", outfitRequest.getImage()).set("updatedBy", outfitRequest.getUpdatedBy())
        .set("updatedDate", new Date());

    this.mongoTemplate.updateMulti(query, update, Outfit.class);
    return this.outfitRepository.findByIdAndIsDeleted(id, 0);
  }

  @Override
  public OutfitCategory saveOutfitCategory(String name) {
    OutfitCategory outfitCategory = new OutfitCategory();
    outfitCategory.setId(idHelper.getNextSequenceId(OutfitCategory.COLLECTION_NAME));
    outfitCategory.setName(name);
    return outfitCategoryRepository.save(outfitCategory);
  }

  @Override
  public List<Outfit> findAll() {
    return outfitRepository.findByIsDeleted(0, Sort.by("_id").ascending());
  }

  @Override
  public List<Outfit> findByOutfitCategoryId(Integer outfitCategoryId) {
    return outfitRepository.findByOutfitCategoryIdAndIsDeleted(outfitCategoryId, 0,
        Sort.by("_id").ascending());
  }

  @Override
  public Outfit findById(Integer id) {
    return outfitRepository.findByIdAndIsDeleted(id, 0);
  }

  @Override
  public boolean deleteOutfit(Integer id) {
    Outfit outfit = this.outfitRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(outfit)) {
      return false;
    }
    this.deleteOutfitById(id);
    return true;
  }

  private void deleteOutfitById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, Outfit.class);
  }

  private void validateFields(OutfitRequest outfitRequest) throws Exception {
    if (outfitRequest.getOutfitCategoryId() == null || outfitRequest.getName() == null ||
        outfitRequest.getQty() == null || outfitRequest.getImage() == null) {
      throw new Exception("Outfit request fields cannot be null");
    }
  }
}
