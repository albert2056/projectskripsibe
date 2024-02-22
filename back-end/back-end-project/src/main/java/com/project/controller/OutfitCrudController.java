package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.model.Outfit;
import com.project.model.request.OutfitRequest;
import com.project.service.outift.OutfitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.OUTFIT)
@Tag(name = "OutfitCrudController", description = "Outfit CRUD Service API")
public class OutfitCrudController {

  private final OutfitService outfitService;

  @PostMapping(ProjectPath.CREATE)
  public Outfit saveOutfit(@RequestBody OutfitRequest outfitRequest) throws Exception {
    return outfitService.saveOutfit(outfitRequest);
  }

  @GetMapping(ProjectPath.FIND_ALL)
  public List<Outfit> getOutfit() throws Exception {
    return outfitService.findAll();
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deleteUser(Integer id) throws Exception {
    return outfitService.deleteOutfit(id);
  }

}
