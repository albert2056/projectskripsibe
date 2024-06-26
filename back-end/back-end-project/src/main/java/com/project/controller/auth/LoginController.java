package com.project.controller.auth;

import com.project.controller.path.ProjectPath;
import com.project.helper.UserHelper;
import com.project.model.response.UserResponse;
import com.project.service.auth.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.LOGIN)
@Tag(name = "LoginController", description = "Login Service API")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @Autowired
  private UserHelper userHelper;

  @PostMapping
  public UserResponse login(@RequestParam String email, @RequestParam String password) throws Exception {
    try {
      UserResponse userResponse = this.loginService.login(email, password);
      return userResponse;
    } catch (Exception e) {
      return this.userHelper.convertToErrorUserResponse(401, e.getMessage());
    }
  }
}
