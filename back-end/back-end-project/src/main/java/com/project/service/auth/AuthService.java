package com.project.service.auth;

import com.project.model.response.UserResponse;

public interface AuthService {
  UserResponse login(String email, String password) throws Exception;
}
