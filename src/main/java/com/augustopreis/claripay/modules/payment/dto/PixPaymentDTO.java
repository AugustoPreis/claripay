package com.augustopreis.claripay.modules.payment.dto;

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
@Schema(description = "Dados de pagamento PIX")
public class PixPaymentDTO {

  @Schema(description = "ID do pagamento", example = "123456789")
  private String paymentId;

  @Schema(description = "Código QR PIX", example = "00020126...")
  private String qrCode;

  @Schema(description = "QR Code em Base64", example = "iVBORw0KGgo...")
  private String qrCodeBase64;

  @Schema(description = "Link de pagamento", example = "https://...")
  private String paymentLink;

  @Schema(description = "Status do pagamento", example = "pending")
  private String status;

  @Schema(description = "Minutos para expiração", example = "30")
  private Integer expirationMinutes;
}
