package com.augustopreis.claripay.modules.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
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
public class UpdateExpenseDTO {

  @Size(min = 3, max = 500, message = "A descrição deve ter entre 3 e 500 caracteres")
  private String description;

  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  private LocalDate date;

  private ExpenseTypeEnum type;
}
