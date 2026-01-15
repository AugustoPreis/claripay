package com.augustopreis.claripay.modules.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;

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
public class ExpenseDTO {
  private Long id;
  private String description;
  private BigDecimal amount;
  private LocalDate date;
  private ExpenseTypeEnum type;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
