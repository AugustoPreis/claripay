package com.augustopreis.claripay.modules.payment.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookPaymentDTO {
  private String action;
  private String type;
  private Long id;
  private Map<String, Object> data;
}
