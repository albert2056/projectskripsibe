package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = User.COLLECTION_NAME)
public class User {
  public static final String COLLECTION_NAME = "users";
  @Id
  private String id;
  private Integer roleId;
  private String name;
  private String phoneNumber;
  private String email;
}
