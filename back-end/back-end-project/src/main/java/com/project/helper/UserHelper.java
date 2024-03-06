package com.project.helper;

import com.project.model.User;
import com.project.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {

  public UserResponse convertUserToUserResponse(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setRoleId(user.getRoleId());
    userResponse.setName(user.getName());
    userResponse.setPhoneNumber(user.getPhoneNumber());
    userResponse.setEmail(user.getEmail());
    userResponse.setPassword(user.getPassword());
    userResponse.setErrorResponse(null);
    return userResponse;
  }

}
