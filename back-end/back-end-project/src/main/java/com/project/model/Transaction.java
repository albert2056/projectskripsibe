package com.project.model;

import com.mongodb.lang.Nullable;
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
  public static final String COLLECTION_NAME = "transaction";
  @Id
  private Integer id;
  @Nullable
  private Integer outfitId;
  private Integer eventId;
  private Integer userId;
  private Integer packageId;
  private String name;
  private Integer totalUsher;
  private Date eventDate;
  private String venue;
  @Nullable
  private String wo;
  private Integer totalPrice;
  private String paymentStatus;
  private Date createdDate;
  private Integer updatedBy;
  private Date updatedDate;
  private Integer isDeleted;
}
