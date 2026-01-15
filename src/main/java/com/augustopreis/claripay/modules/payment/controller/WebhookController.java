package com.augustopreis.claripay.modules.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.modules.payment.dto.WebhookPaymentDTO;
import com.augustopreis.claripay.modules.payment.usecase.ProcessPaymentWebhookUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class WebhookController {

  private final ProcessPaymentWebhookUseCase processPaymentWebhook;

  @PostMapping("/payment")
  public ResponseEntity<Void> handlePaymentWebhook(@RequestBody WebhookPaymentDTO webhook) {
    if (isPaymentWebhook(webhook)) {
      processPaymentWebhook.execute(webhook.getId());
    }

    return ResponseEntity.ok().build();
  }

  private boolean isPaymentWebhook(WebhookPaymentDTO webhook) {
    return "payment".equals(webhook.getType());
  }
}
