package com.augustopreis.claripay.modules.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Dados para criar despesa")
public class CreateExpenseDTO {

  @Schema(description = "Descrição", example = "Aluguel do escritório")
  @NotBlank(message = "A descrição é obrigatória")
  @Size(min = 3, max = 500, message = "A descrição deve ter entre 3 e 500 caracteres")
  private String description;

  @Schema(description = "Valor", example = "2000.00")
  @NotNull(message = "O valor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Schema(description = "Data", example = "2024-01-15")
  @NotNull(message = "A data é obrigatória")
  private LocalDate date;

  @Schema(description = "Tipo de despesa")
  @NotNull(message = "O tipo de despesa é obrigatório")
  private ExpenseTypeEnum type;
}
