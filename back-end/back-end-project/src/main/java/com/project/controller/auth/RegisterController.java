package com.project.controller.auth;

import com.project.controller.path.ProjectPath;
import com.project.helper.IdHelper;
import com.project.helper.UserHelper;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.REGISTER)
@Tag(name = "RegisterController", description = "Register Service API")
public class RegisterController {
  private final UserService userService;
  private final UserHelper userHelper;
  private final IdHelper idHelper;

  @PostMapping
  public UserResponse register(@RequestBody UserRequest userRequest) throws Exception {
    try {
      return userService.register(userRequest);
    } catch (Exception e) {
      this.idHelper.decrementSequenceId(User.COLLECTION_NAME);
      return this.userHelper.convertToErrorUserResponse(401, e.getMessage());
    }
  }
}
