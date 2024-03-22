package com.project.impl.auth;

import com.project.helper.ErrorMessage;
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

import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserHelper userHelper;

  @Override
  public UserResponse login(String email, String password) throws Exception {
    User user = this.getUser(email);
    if (!authenticateUser(user, email, password)) {
      throw new Exception(ErrorMessage.LOGIN_NOT_MATCH);
    }
    return this.userHelper.convertUserToUserResponse(user);
  }

  private User getUser(String email) throws Exception {
    User user = userRepository.findFirstByEmailAndIsDeleted(email, 0);
    if (Objects.isNull(user)) {
      throw new Exception(ErrorMessage.USER_NOT_FOUND);
    }
    return user;
  }

  private boolean authenticateUser(User user, String email, String password) {
    return user.getPassword().equals(this.userHelper.encodePassword(password));
  }
}
