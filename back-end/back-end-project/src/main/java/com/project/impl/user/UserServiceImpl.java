package com.project.impl.user;

import com.project.helper.IdHelper;
import com.project.helper.PasswordEncoder;
import com.project.helper.UserHelper;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import com.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private UserHelper userHelper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserResponse saveUser(UserRequest userRequest) throws Exception {
    User user = new User();
    user.setId(idHelper.getNextSequenceId(User.COLLECTION_NAME));
    user.setRoleId(userRequest.getRoleId());
    user.setName(userRequest.getName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    if (!isAdmin(userRequest.getRoleId())) {
      this.validateEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
    }
    user.setEmail(userRequest.getEmail());
    user.setPassword(this.encodePassword(userRequest.getPassword()));
    userRepository.save(user);
    return userHelper.convertUserToUserResponse(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public boolean deleteUser(Integer id) {
    User user = userRepository.findById(id);
    if (Objects.isNull(user)) {
      return false;
    }
    userRepository.delete(user);
    return true;
  }

  private String encodePassword(String password) {
    return this.passwordEncoder.encode(password);
  }

  private Boolean isAdmin(Integer roleId) {
    return roleId == 1;
  }

  private void validateEmailAndPassword(String email, String password) throws Exception {
    if (!validateEmail(email)) {
      throw new Exception("Register failed! email must contain '@'");
    }
    if (!validatePassword(password)) {
      throw new Exception("Register failed! password length must be 8 or more");
    }
  }

  private Boolean validateEmail(String email) {
    return email.contains("@");
  }
  private Boolean validatePassword(String password) {
    return password.length() >= 8;
  }
}
