package com.augustopreis.claripay.modules.payment.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessPaymentWebhookUseCase {

  private final ChargeRepository chargeRepository;

  @Transactional
  public void execute(Long paymentId) {
    try {
      Payment payment = fetchPayment(paymentId);

      if (isPaymentApproved(payment)) {
        processApprovedPayment(payment);
      }
    } catch (Exception e) {
      throw new RuntimeException("Erro ao processar webhook: " + e.getMessage());
    }
  }

  private Payment fetchPayment(Long paymentId) throws Exception {
    PaymentClient client = new PaymentClient();

    return client.get(paymentId);
  }

  private boolean isPaymentApproved(Payment payment) {
    return "approved".equals(payment.getStatus());
  }

  private void processApprovedPayment(Payment payment) {
    Long chargeId = extractChargeId(payment);
    Charge charge = findCharge(chargeId);

    updateChargeAsPaid(charge);
  }

  private Long extractChargeId(Payment payment) {
    return Long.valueOf(payment.getMetadata().get("charge_id").toString());
  }

  private Charge findCharge(Long chargeId) {
    return chargeRepository.findById(chargeId)
        .orElseThrow(() -> new RuntimeException("Cobrança não encontrada: " + chargeId));
  }

  private void updateChargeAsPaid(Charge charge) {
    charge.setStatus(ChargeStatusEnum.PAID);
    charge.setPaidAt(LocalDateTime.now());

    chargeRepository.save(charge);
  }
}
