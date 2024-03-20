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
@Document(collection = Transaction.COLLECTION_NAME)
public class Transaction {
  public static final String COLLECTION_NAME = "users";
  @Id
  private Integer id;
  private Integer outfitId;
  private Integer eventId;
  private Integer userId;
  private Integer packageId;
  private String name;
  private Integer totalUsher;
  private Date eventDate;
  private String venue;
  private String wo;
  private Integer totalPrice;
  private String paymentStatus;
  private Date createdDate;
  private String updatedBy;
  private Date updatedDate;
  private Boolean isDeleted;
}
