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
@Document(collection = Product.COLLECTION_NAME)
public class Product {
  public static final String COLLECTION_NAME = "products";
  @Id
  private String productId;
  private String name;
  private Integer price;
}
