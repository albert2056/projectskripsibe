package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = User.COLLECTION_NAME)
public class User {
  public static final String COLLECTION_NAME = "users";
  @Id
  private Integer id;
  private Integer roleId;
  private String name;
  private String phoneNumber;
  private String email;
  private String password;
  private Date createdDate;
  private Integer isDeleted;
}
