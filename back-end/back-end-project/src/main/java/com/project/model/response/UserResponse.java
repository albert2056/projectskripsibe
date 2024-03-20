package com.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  private Integer id;
  private String role;
  private String name;
  private String phoneNumber;
  private String email;
  private String password;
  private Integer statusCode;
  private String description;
}
