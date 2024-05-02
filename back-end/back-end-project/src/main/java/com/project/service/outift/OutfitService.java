package com.project.service.outift;

import com.project.model.Outfit;
import com.project.model.OutfitCategory;
import com.project.model.request.OutfitRequest;

import java.util.List;

public interface OutfitService {
  Outfit saveOutfit(OutfitRequest outfitRequest) throws Exception;
  OutfitCategory saveOutfitCategory(String name);
  List<Outfit> findAll();
  List<Outfit> findByOutfitCategoryId(Integer outfitCategoryId);
  Outfit findById(Integer id);
  boolean deleteOutfit(Integer id);
}
