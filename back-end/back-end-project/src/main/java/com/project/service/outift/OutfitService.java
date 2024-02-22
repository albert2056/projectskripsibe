package com.project.service.outift;

import com.project.model.Outfit;
import com.project.model.request.OutfitRequest;

import java.util.List;

public interface OutfitService {
  Outfit saveOutfit(OutfitRequest outfitRequest);
  List<Outfit> findAll();
  boolean deleteOutfit(Integer id);
}
