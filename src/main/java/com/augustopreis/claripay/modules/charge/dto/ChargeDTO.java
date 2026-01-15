package com.augustopreis.claripay.modules.charge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;

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
public class ChargeDTO {
  private Long id;
  private BigDecimal amount;
  private String description;
  private LocalDate dueDate;
  private ChargeStatusEnum status;
  private LocalDateTime paidAt;
  private String pixCode;
  private String paymentLink;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long customerId;
  private String customerName;
  private Long serviceId;
  private String serviceName;
}
