package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutfitRequest {
  private Integer outfitCategoryId;
  private String name;
  private Integer qty;
  private String image;
  private String updatedBy;
}
