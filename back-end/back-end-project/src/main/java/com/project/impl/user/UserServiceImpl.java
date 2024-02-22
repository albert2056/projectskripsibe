package com.project.impl.user;

import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.repository.UserRepository;
import com.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User saveUser(UserRequest userRequest) {
    User user = new User();
    user.setRoleId(userRequest.getRoleId());
    user.setName(userRequest.getName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    user.setEmail(userRequest.getEmail());
    userRepository.save(user);
    return user;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public boolean deleteUser(String id) {
    User user = userRepository.findById(id).orElse(null);
    if (Objects.isNull(user)) {
      return false;
    }
    userRepository.delete(user);
    return true;
  }
}
