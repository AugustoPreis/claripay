package com.augustopreis.claripay.modules.withdrawal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criar retirada")
public class CreateWithdrawalDTO {

  @Schema(description = "Descrição", example = "Pró-labore")
  @NotBlank(message = "A descrição é obrigatória")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Schema(description = "Valor", example = "5000.00")
  @NotNull(message = "O valor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Schema(description = "Data", example = "2024-01-31")
  @NotNull(message = "A data é obrigatória")
  private LocalDate date;

  @Schema(description = "Tipo de retirada")
  @NotNull(message = "O tipo de retirada é obrigatório")
  private WithdrawalTypeEnum type;
}
