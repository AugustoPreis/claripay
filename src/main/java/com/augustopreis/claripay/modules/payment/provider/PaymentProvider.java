package com.augustopreis.claripay.modules.payment.provider;

import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.augustopreis.claripay.modules.payment.dto.PixPaymentDTO;

public interface PaymentProvider {

  PixPaymentDTO generatePixPayment(Charge charge) throws Exception;

  String generatePaymentLink(Charge charge) throws Exception;

  boolean validateWebhookSignature(String signature, String payload);
}
