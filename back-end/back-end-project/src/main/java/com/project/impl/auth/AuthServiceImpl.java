package com.project.impl.auth;

import com.project.helper.PasswordEncoder;
import com.project.helper.UserHelper;
import com.project.model.User;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import com.project.service.auth.AuthService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserHelper userHelper;

  @Override
  public UserResponse login(String email, String password) throws Exception {
    User user = userRepository.findByEmail(email);
    if (!authenticateUser(user, email, password)) {
      throw new Exception("Log in failed! Email and Password does not match!");
    }
    return this.userHelper.convertUserToUserResponse(user);
  }

  public boolean authenticateUser(User user, String email, String password) {
    if (StringUtils.isNotBlank(email)) {
      return user.getPassword().equals(this.passwordEncoder.encode(password));
    }
    return false;
  }
}
