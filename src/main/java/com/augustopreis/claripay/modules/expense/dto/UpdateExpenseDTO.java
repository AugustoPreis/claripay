package com.augustopreis.claripay.modules.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para atualizar despesa")
public class UpdateExpenseDTO {

  @Schema(description = "Descrição", example = "Aluguel do escritório")
  @Size(min = 3, max = 500, message = "A descrição deve ter entre 3 e 500 caracteres")
  private String description;

  @Schema(description = "Valor", example = "2000.00")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Schema(description = "Data", example = "2024-01-15")
  private LocalDate date;

  @Schema(description = "Tipo de despesa")
  private ExpenseTypeEnum type;
}
