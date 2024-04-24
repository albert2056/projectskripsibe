package com.project.model.response;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
  @Nullable
  private Integer id;
  @Nullable
  private Integer outfitId;
  @Nullable
  private Integer eventId;
  @Nullable
  private Integer userId;
  @Nullable
  private Integer packageId;
  @Nullable
  private String name;
  @Nullable
  private Integer totalUsher;
  @Nullable
  private Date eventDate;
  @Nullable
  private String venue;
  @Nullable
  private String wo;
  @Nullable
  private Integer totalPrice;
  @Nullable
  private String paymentStatus;
  @Nullable
  private Date createdDate;
  @Nullable
  private Integer updatedBy;
  @Nullable
  private Date updatedDate;
  private Integer statusCode;
  private String description;
}
