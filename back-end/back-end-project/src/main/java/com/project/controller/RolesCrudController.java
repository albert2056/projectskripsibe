package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.model.Roles;
import com.project.service.roles.RolesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.ROLES)
@Tag(name = "RolesCrudController", description = "Roles CRUD Service API")
public class RolesCrudController {

  private final RolesService rolesService;

  @PostMapping(ProjectPath.CREATE)
  public Roles saveRole(String name) {
    return this.rolesService.saveRole(name);
  }
}
