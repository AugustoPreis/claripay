package com.augustopreis.claripay.modules.payment.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do webhook de pagamento")
public class WebhookPaymentDTO {

  @Schema(description = "Ação", example = "payment.updated")
  private String action;

  @Schema(description = "Tipo", example = "payment")
  private String type;

  @Schema(description = "ID do pagamento", example = "123456789")
  private Long id;

  @Schema(description = "Dados adicionais")
  private Map<String, Object> data;
}
