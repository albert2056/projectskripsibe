package com.project.impl.user;

import com.project.helper.ErrorMessage;
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
  private MongoTemplate mongoTemplate;

  @Override
  public UserResponse register(UserRequest userRequest) throws Exception {
    User user = new User();
    user.setId(idHelper.getNextSequenceId(User.COLLECTION_NAME));
    user.setRoleId(userRequest.getRoleId());
    this.validateNameAndPhoneNumber(userRequest.getName(), userRequest.getPhoneNumber());
    user.setName(userRequest.getName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    this.validateEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setPassword(this.userHelper.encodePassword(userRequest.getPassword()));
    user.setCreatedDate(new Date());
    user.setIsDeleted(0);
    userRepository.save(user);
    return userHelper.convertUserToUserResponse(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findByIsDeleted(0);
  }

  @Override
  public boolean deleteUser(Integer id) {
    User user = userRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(user)) {
      return false;
    }
    this.deleteUserById(id);
    return true;
  }

  private void validateNameAndPhoneNumber(String name, String phoneNumber) throws Exception {
    if (name == null || name.isBlank()) {
      throw new Exception(ErrorMessage.NAME);
    }
    this.validatePhoneNumber(phoneNumber);
  }

  private void validateEmailAndPassword(String email, String password) throws Exception {
    User user = userRepository.findFirstByEmailAndIsDeleted(email, 0);
    if (!Objects.isNull(user)) {
      throw new Exception(ErrorMessage.DUPLICATE_EMAIL);
    }
    if (!validateEmail(email)) {
      throw new Exception(ErrorMessage.EMAIL);
    }
    if (!validatePassword(password)) {
      throw new Exception(ErrorMessage.PASSWORD_LENGTH);
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
      throw new Exception(ErrorMessage.PASSWORD_UPPERCASE);
    }
    if (!password.matches(".*[a-z].*")) {
      throw new Exception(ErrorMessage.PASSWORD_LOWERCASE);
    }
    if (!password.matches(".*\\d.*")) {
      throw new Exception(ErrorMessage.PASSWORD_NUMBER);
    }
    return true;
  }

  private void validatePhoneNumber(String phoneNumber) throws Exception {
    if (phoneNumber.length()<10) {
      throw new Exception(ErrorMessage.PHONENUM_LENGTH);
    }
    if (!phoneNumber.matches("\\d+")) {
      throw new Exception(ErrorMessage.PHONENUM_NUMBER);
    }
  }

  private void deleteUserById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, User.class);
  }
}
