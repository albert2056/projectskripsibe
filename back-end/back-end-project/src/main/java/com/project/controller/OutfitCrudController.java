package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.helper.OutfitHelper;
import com.project.model.OutfitCategory;
import com.project.model.request.OutfitRequest;
import com.project.model.response.OutfitResponse;
import com.project.service.outift.OutfitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.OUTFIT)
@Tag(name = "OutfitCrudController", description = "Outfit CRUD Service API")
public class OutfitCrudController {

  private final OutfitService outfitService;
  private final OutfitHelper outfitHelper;

  @PostMapping(ProjectPath.CREATE)
  public OutfitResponse saveOutfit(@RequestBody OutfitRequest outfitRequest) throws Exception {
    try {
      return outfitHelper.convertToOutfitResponse(outfitService.saveOutfit(outfitRequest));
    } catch (Exception e) {
      return this.outfitHelper.convertToErrorOutfitResponse(401, e.getMessage());
    }
  }

  @PostMapping(ProjectPath.UPDATE)
  public OutfitResponse updateOutfit(@RequestParam Integer id, @RequestBody OutfitRequest outfitRequest) throws Exception {
    try {
      return outfitHelper.convertToOutfitResponse(outfitService.updateOutfit(id, outfitRequest));
    } catch (Exception e) {
      return this.outfitHelper.convertToErrorOutfitResponse(401, e.getMessage());
    }
  }

  @PostMapping(ProjectPath.CATEGORY + ProjectPath.CREATE)
  public OutfitCategory saveOutfitCategory(@RequestParam String name) throws Exception {
    return outfitService.saveOutfitCategory(name);
  }

  @GetMapping(ProjectPath.FIND_ALL)
  public List<OutfitResponse> getAllOutfit() throws Exception {
    return outfitHelper.convertToListOutfitResponse(outfitService.findAll());
  }

  @GetMapping(ProjectPath.FIND_BY_OUTFIT_CATEGORY_ID)
  public List<OutfitResponse> findOutfitsByOutfitCategoryId(@RequestParam Integer outfitCategoryId) throws Exception {
    return outfitHelper.convertToListOutfitResponse(outfitService.findByOutfitCategoryId(outfitCategoryId));
  }

  @GetMapping(ProjectPath.FIND_BY_ID)
  public OutfitResponse findOutfitById(@RequestParam Integer id) throws Exception {
    return outfitHelper.convertToOutfitResponse(outfitService.findById(id));
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deleteOutfit(Integer id) throws Exception {
    return outfitService.deleteOutfit(id);
  }

}
