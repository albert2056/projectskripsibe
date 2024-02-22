package com.project.impl.user;

import com.project.helper.IdHelper;
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

  @Autowired
  private IdHelper idHelper;

  @Override
  public User saveUser(UserRequest userRequest) {
    User user = new User();
    user.setId(idHelper.getNextSequenceId(User.COLLECTION_NAME));
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
  public boolean deleteUser(Integer id) {
    User user = userRepository.findById(id);
    if (Objects.isNull(user)) {
      return false;
    }
    userRepository.delete(user);
    return true;
  }
}
