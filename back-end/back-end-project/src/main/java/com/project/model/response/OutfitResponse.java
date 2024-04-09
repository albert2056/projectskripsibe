package com.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutfitResponse {
  private Integer id;
  private Integer outfitCategoryId;
  private String name;
  private Integer qty;
  private String image;
  private Integer updatedBy;
  private Date updatedDate;
  private Date createdDate;
  private Integer statusCode;
  private String description;
}
