package com.augustopreis.claripay.modules.customer.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
  private Long id;
  private String name;
  private String document;
  private String email;
  private String cellphone;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
