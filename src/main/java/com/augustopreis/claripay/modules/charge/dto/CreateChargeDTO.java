package com.augustopreis.claripay.modules.charge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
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
public class CreateChargeDTO {

  @NotNull(message = "O valor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @NotNull(message = "A data de vencimento é obrigatória")
  private LocalDate dueDate;

  @NotNull(message = "O cliente é obrigatório")
  private Long customerId;

  private Long serviceId;

  @Size(max = 500, message = "O código Pix deve ter no máximo 500 caracteres")
  private String pixCode;

  @Size(max = 500, message = "O link de pagamento deve ter no máximo 500 caracteres")
  private String paymentLink;
}
