package com.project.service.user;

import com.project.model.User;
import com.project.model.request.UserRequest;

import java.util.List;

public interface UserService {
  User saveUser(UserRequest userRequest);
  List<User> findAll();
  boolean deleteUser(Integer id);
}
