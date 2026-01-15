package com.augustopreis.claripay.modules.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;

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
public class ServiceDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal defaultValue;
  private RecurrenceTypeEnum recurrenceType;
  private Integer recurrenceInterval;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
