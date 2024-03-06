package com.project.impl.outfit;

import com.project.helper.IdHelper;
import com.project.model.Outfit;
import com.project.model.request.OutfitRequest;
import com.project.repository.OutfitRepository;
import com.project.service.outift.OutfitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OutfitServiceImpl implements OutfitService {

  @Autowired
  private OutfitRepository outfitRepository;

  @Autowired
  private IdHelper idHelper;

  @Override
  public Outfit saveOutfit(OutfitRequest outfitRequest) {
    Outfit outfit = new Outfit();
    outfit.setId(idHelper.getNextSequenceId(Outfit.COLLECTION_NAME));
    outfit.setOutfitCategoryId(outfitRequest.getOutfitCategoryId());
    outfit.setName(outfitRequest.getName());
    outfit.setQty(outfitRequest.getQty());
    outfit.setImage(outfitRequest.getImage());
    outfitRepository.save(outfit);
    return outfit;
  }

  @Override
  public List<Outfit> findAll() {
    return outfitRepository.findAll();
  }

  @Override
  public boolean deleteOutfit(Integer id) {
    Outfit outfit = outfitRepository.findById(id);
    if (Objects.isNull(outfit)) {
      return false;
    }
    outfitRepository.delete(outfit);
    return true;
  }

}
