package com.augustopreis.claripay.modules.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.modules.payment.dto.WebhookPaymentDTO;
import com.augustopreis.claripay.modules.payment.usecase.ProcessPaymentWebhookUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
@Tag(name = "Webhooks", description = "Recebe notificações de pagamento")
public class WebhookController {

  private final ProcessPaymentWebhookUseCase processPaymentWebhook;

  @PostMapping("/payment")
  @Operation(summary = "Webhook de pagamento", description = "Recebe notificações do Mercado Pago sobre pagamentos")
  @ApiOk
  @SecurityRequirements
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
