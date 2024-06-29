package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.helper.UserHelper;
import com.project.model.response.UserResponse;
import com.project.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.USER)
@Tag(name = "UserCrudController", description = "User CRUD Service API")
public class UserCrudController {

  private final UserService userService;
  private final UserHelper userHelper;

  @GetMapping(ProjectPath.FIND_ALL)
  public List<UserResponse> viewUsers() throws Exception {
    return userHelper.convertUserToUserResponses(userService.findAll());
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deleteUser(Integer id) throws Exception {
    return userService.deleteUser(id);
  }
}
