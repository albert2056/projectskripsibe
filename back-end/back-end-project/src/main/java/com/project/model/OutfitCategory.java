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
@Document(collection = OutfitCategory.COLLECTION_NAME)
public class OutfitCategory {
  public static final String COLLECTION_NAME = "outfitCategory";
  @Id
  private Integer id;
  private String name;
}
