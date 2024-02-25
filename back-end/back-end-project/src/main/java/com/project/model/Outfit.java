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
@Document(collection = Outfit.COLLECTION_NAME)
public class Outfit {
  public static final String COLLECTION_NAME = "outfit";
  @Id
  private Integer id;
  private Integer outfitCategoryId;
  private String name;
  private Integer qty;
  private String image;
}
