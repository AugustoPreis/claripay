package com.augustopreis.claripay.modules.payment.provider;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.exception.MercadoPagoException;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.augustopreis.claripay.modules.payment.dto.PixPaymentDTO;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MercadoPagoProvider implements PaymentProvider {

  @Value("${payment.pix.expiration-minutes:30}")
  private Integer expirationMinutes;

  @Value("${payment.mercadopago.notification-url:}")
  private String notificationUrl;

  @Override
  public PixPaymentDTO generatePixPayment(Charge charge) throws Exception {
    PaymentClient client = new PaymentClient();

    Map<String, Object> metadata = new HashMap<>();
    metadata.put("charge_id", charge.getId().toString());
    metadata.put("user_id", charge.getUser().getId().toString());
    metadata.put("customer_id", charge.getCustomer().getId().toString());

    PaymentPayerRequest payer = PaymentPayerRequest.builder()
        .email(charge.getCustomer().getEmail())
        .firstName(charge.getCustomer().getName())
        .build();

    PaymentCreateRequest.PaymentCreateRequestBuilder requestBuilder = PaymentCreateRequest.builder()
        .transactionAmount(charge.getAmount())
        .description(charge.getDescription() != null ? charge.getDescription() : "Cobrança ClariPay")
        .paymentMethodId("pix")
        .metadata(metadata)
        .dateOfExpiration(OffsetDateTime.now().plusMinutes(expirationMinutes))
        .payer(payer);

    if (notificationUrl != null && !notificationUrl.isEmpty()) {
      requestBuilder.notificationUrl(notificationUrl);
    }

    try {
      PaymentCreateRequest request = requestBuilder.build();
      Payment payment = client.create(request);

      return PixPaymentDTO.builder()
          .paymentId(payment.getId().toString())
          .qrCode(payment.getPointOfInteraction().getTransactionData().getQrCode())
          .qrCodeBase64(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
          .paymentLink(payment.getPointOfInteraction().getTransactionData().getTicketUrl())
          .status(payment.getStatus())
          .expirationMinutes(expirationMinutes)
          .build();
    } catch (MPApiException e) {
      throw new MercadoPagoException(e.getApiResponse().getContent());
    }
  }

  @Override
  public String generatePaymentLink(Charge charge) throws Exception {
    PixPaymentDTO payment = generatePixPayment(charge);
    return payment.getPaymentLink();
  }

  @Override
  public boolean validateWebhookSignature(String signature, String payload) {
    // TODO: Implementar validação de assinatura do Mercado Pago
    return true;
  }
}
