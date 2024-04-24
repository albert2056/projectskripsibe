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
public class PortfolioResponse {
  private Integer id;
  private String image;
  private String gownName;
  private Date eventDate;
  private String venue;
  private String wo;
  private Integer column;
  private String name;
  private String eventName;
  private Integer isDeleted;
  private Integer statusCode;
  private String description;
}
