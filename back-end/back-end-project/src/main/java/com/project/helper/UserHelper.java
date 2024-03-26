package com.project.helper;

import com.project.model.User;
import com.project.model.response.UserResponse;
import com.project.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserHelper {

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserResponse convertUserToUserResponse(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setRole(this.getRole(user.getRoleId()));
    userResponse.setName(user.getName());
    userResponse.setPhoneNumber(user.getPhoneNumber());
    userResponse.setEmail(user.getEmail());
    userResponse.setPassword(user.getPassword());
    userResponse.setStatusCode(null);
    userResponse.setDescription(null);
    return userResponse;
  }

  public UserResponse convertToErrorUserResponse(Integer code, String description) {
    UserResponse userResponse = new UserResponse();
    userResponse.setStatusCode(code);
    userResponse.setDescription(description);
    return userResponse;
  }

  public List<UserResponse> convertUserToUserResponses(List<User> users) {
    List<UserResponse> userResponses = new ArrayList<>();
    for (User user : users) {
      userResponses.add(this.convertUserToUserResponse(user));
    }
    return userResponses;
  }

  public String encodePassword(String password) {
    return this.passwordEncoder.encode(password);
  }

  private String getRole(Integer roleId) {
    return rolesRepository.findById(roleId).getName();
  }
}
