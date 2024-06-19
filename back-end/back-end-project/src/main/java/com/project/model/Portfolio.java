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
@Document(collection = Portfolio.COLLECTION_NAME)
public class Portfolio {
  public static final String COLLECTION_NAME = "portfolio";
  @Id
  private Integer id;
  private String image;
  private String gownName;
  private Date eventDate;
  private String venue;
  private String wo;
  private String name;
  private Integer isDeleted;
}
