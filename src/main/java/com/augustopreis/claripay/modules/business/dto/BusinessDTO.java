package com.augustopreis.claripay.modules.business.dto;

import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.business.enums.BusinessTypeEnum;

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
public class BusinessDTO {
  private Long id;
  private String name;
  private String description;
  private String document;
  private String email;
  private String cellphone;
  private BusinessTypeEnum type;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}