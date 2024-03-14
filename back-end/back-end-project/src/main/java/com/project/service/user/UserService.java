package com.project.service.user;

import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;

import java.util.List;

public interface UserService {
  UserResponse saveUser(UserRequest userRequest) throws Exception;
  List<User> findAll();
  boolean deleteUser(Integer id);
}
