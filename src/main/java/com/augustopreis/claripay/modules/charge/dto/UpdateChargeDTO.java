package com.augustopreis.claripay.modules.charge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Schema(description = "Dados para atualizar cobrança")
public class UpdateChargeDTO {

  @Schema(description = "Valor", example = "150.00")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  private BigDecimal amount;

  @Schema(description = "Descrição", example = "Consultoria Mensal")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Schema(description = "Data de vencimento", example = "2024-12-31")
  private LocalDate dueDate;

  @Schema(description = "ID do cliente", example = "1")
  private Long customerId;

  @Schema(description = "ID do serviço", example = "1")
  private Long serviceId;

  @Schema(description = "Código PIX", example = "00020126...")
  @Size(max = 500, message = "O código Pix deve ter no máximo 500 caracteres")
  private String pixCode;

  @Schema(description = "Link de pagamento", example = "https://...")
  @Size(max = 500, message = "O link de pagamento deve ter no máximo 500 caracteres")
  private String paymentLink;
}
