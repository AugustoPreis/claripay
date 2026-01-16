package com.augustopreis.claripay.modules.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Despesa")
public class ExpenseDTO {
  
  @Schema(description = "ID", example = "1")
  private Long id;
  
  @Schema(description = "Descrição", example = "Aluguel do escritório")
  private String description;
  
  @Schema(description = "Valor", example = "2000.00")
  private BigDecimal amount;
  
  @Schema(description = "Data", example = "2024-01-15")
  private LocalDate date;
  
  @Schema(description = "Tipo")
  private ExpenseTypeEnum type;
  
  @Schema(description = "Ativo", example = "true")
  private Boolean active;
  
  @Schema(description = "Data de criação", example = "2024-01-01T10:00:00")
  private LocalDateTime createdAt;
  
  @Schema(description = "Data de atualização", example = "2024-01-15T14:30:00")
  private LocalDateTime updatedAt;
}
