package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequest {
  private String image;
  private Integer outfitId;
  private Date eventDate;
  private String venue;
  private String wo;
  private String name;
}
