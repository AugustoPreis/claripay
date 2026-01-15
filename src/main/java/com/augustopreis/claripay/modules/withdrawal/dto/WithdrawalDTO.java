package com.augustopreis.claripay.modules.withdrawal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalDTO {

  private Long id;
  private String description;
  private BigDecimal amount;
  private LocalDate date;
  private WithdrawalTypeEnum type;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
