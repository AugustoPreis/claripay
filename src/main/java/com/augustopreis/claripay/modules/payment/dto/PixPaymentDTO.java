package com.augustopreis.claripay.modules.payment.dto;

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
public class PixPaymentDTO {
  private String paymentId;
  private String qrCode;
  private String qrCodeBase64;
  private String paymentLink;
  private String status;
  private Integer expirationMinutes;
}
