package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = Package.COLLECTION_NAME)
public class Package {
  public static final String COLLECTION_NAME = "package";
  @Id
  private Integer id;
  private String name;
  private Integer price;
  private List<String> description;
  private Integer isDeleted;
}
