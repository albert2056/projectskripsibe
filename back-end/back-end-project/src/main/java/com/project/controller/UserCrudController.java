package com.project.controller;

import com.project.controller.path.ProjectPath;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.service.user.UserService;
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
@RequestMapping(value = ProjectPath.USER)
public class UserCrudController {

  private final UserService userService;

  @PostMapping(ProjectPath.CREATE)
  public User saveUser(@RequestBody UserRequest userRequest) throws Exception {
    return userService.saveUser(userRequest);
  }

  @GetMapping(ProjectPath.FIND_ALL)
  public List<User> getUsers() throws Exception {
    return userService.findAll();
  }

  @DeleteMapping(ProjectPath.DELETE)
  public boolean deleteUser(String id) throws Exception {
    return userService.deleteUser(id);
  }
}
