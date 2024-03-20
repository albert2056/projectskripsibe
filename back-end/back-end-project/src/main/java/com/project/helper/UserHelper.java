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

  public List<UserResponse> convertUserToUserResponses(List<User> users) {
    List<UserResponse> userResponses = new ArrayList<>();
    for (User user : users) {
      userResponses.add(this.convertUserToUserResponse(user));
    }
    return userResponses;
  }

  private String getRole(Integer roleId) {
    return rolesRepository.findById(roleId).getName();
  }
}
