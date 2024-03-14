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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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

  @Autowired
  private MongoTemplate mongoTemplate;

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
    user.setCreatedDate(new Date());
    user.setIsDeleted(false);
    userRepository.save(user);
    return userHelper.convertUserToUserResponse(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findByIsDeletedFalse();
  }

  @Override
  public boolean deleteUser(Integer id) {
    User user = userRepository.findByIdAndIsDeletedFalse(id);
    if (Objects.isNull(user)) {
      return false;
    }
    this.deleteUserById(id);
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
      throw new Exception("Register failed! Email must contain '@'");
    }
    if (!validatePassword(password)) {
      throw new Exception("Register failed! Password length must be 8 or more");
    }
  }

  private Boolean validateEmail(String email) {
    return email.contains("@");
  }

  private Boolean validatePassword(String password) throws Exception {
    return password.length() >= 8 && validateLetterAndNumber(password);
  }

  private Boolean validateLetterAndNumber(String password) throws Exception {
    if (!password.matches(".*[A-Z].*")) {
      throw new Exception("Register failed! Password must have at least one uppercase letter.");
    }
    if (!password.matches(".*[a-z].*")) {
      throw new Exception("Register failed! Password must have at least one lowercase letter.");
    }
    if (!password.matches(".*\\d.*")) {
      throw new Exception("Register failed! Password must have at least one numeric digit.");
    }
    return true;
  }

  private void deleteUserById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", true);

    this.mongoTemplate.updateMulti(query, update, User.class);
  }
}
