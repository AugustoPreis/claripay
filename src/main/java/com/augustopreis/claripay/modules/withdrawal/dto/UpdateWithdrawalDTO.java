package com.augustopreis.claripay.modules.withdrawal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualizar retirada")
public class UpdateWithdrawalDTO {

  @Schema(description = "Descrição", example = "Pró-labore")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Schema(description = "Valor", example = "5000.00")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Schema(description = "Data", example = "2024-01-31")
  private LocalDate date;

  @Schema(description = "Tipo de retirada")
  private WithdrawalTypeEnum type;
}
