package com.augustopreis.claripay.modules.withdrawal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;

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
public class CreateWithdrawalDTO {

  @NotBlank(message = "A descrição é obrigatória")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @NotNull(message = "O valor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @NotNull(message = "A data é obrigatória")
  private LocalDate date;

  @NotNull(message = "O tipo de retirada é obrigatório")
  private WithdrawalTypeEnum type;
}
