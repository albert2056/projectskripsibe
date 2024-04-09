package com.project.helper;

import com.project.model.Outfit;
import com.project.model.response.OutfitResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OutfitHelper {

  public OutfitResponse convertToOutfitResponse(Outfit outfit) {
    OutfitResponse outfitResponse = new OutfitResponse();
    outfitResponse.setId(outfit.getId());
    outfitResponse.setOutfitCategoryId(outfit.getOutfitCategoryId());
    outfitResponse.setName(outfit.getName());
    outfitResponse.setQty(outfit.getQty());
    outfitResponse.setImage(outfit.getImage());
    outfitResponse.setUpdatedBy(outfit.getUpdatedBy());
    outfitResponse.setUpdatedDate(outfit.getUpdatedDate());
    outfitResponse.setCreatedDate(outfit.getCreatedDate());
    return outfitResponse;
  }

  public List<OutfitResponse> convertToListOutfitResponse(List<Outfit> outfits) {
    List<OutfitResponse> outfitResponses = new ArrayList<>();
    for (Outfit outfit : outfits) {
      outfitResponses.add(this.convertToOutfitResponse(outfit));
    }
    return outfitResponses;
  }

  public OutfitResponse convertToErrorOutfitResponse(Integer code, String description) {
    OutfitResponse outfitResponse = new OutfitResponse();
    outfitResponse.setStatusCode(code);
    outfitResponse.setDescription(description);
    return outfitResponse;
  }
}
