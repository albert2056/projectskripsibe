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
@Document(collection = Roles.COLLECTION_NAME)
public class Roles {
  @Id
  public static final String COLLECTION_NAME = "roles";
  private Integer id;
  private String name;
}
