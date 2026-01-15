package com.augustopreis.claripay.modules.payment.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.augustopreis.claripay.modules.payment.dto.PixPaymentDTO;
import com.augustopreis.claripay.modules.payment.provider.PaymentProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateChargePixUseCase {

  private final ChargeRepository chargeRepository;
  private final PaymentProvider paymentProvider;
  private final AuthenticationUtil auth;

  @Transactional
  public PixPaymentDTO execute(Long chargeId) {
    Long userId = auth.getAuthenticatedId();

    Charge charge = findCharge(chargeId, userId);
    validateChargeStatus(charge);

    try {
      PixPaymentDTO pixPayment = paymentProvider.generatePixPayment(charge);
      updateChargeWithPixData(charge, pixPayment);

      return pixPayment;
    } catch (Exception e) {
      throw new BusinessException("Não foi possível gerar o PIX, tente novamente mais tarde.");
    }
  }

  private Charge findCharge(Long chargeId, Long userId) {
    return chargeRepository
        .findByIdAndUserIdAndActiveTrue(chargeId, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cobrança não encontrada"));
  }

  private void validateChargeStatus(Charge charge) {
    switch (charge.getStatus()) {
      case PAID -> throw new BusinessException("Cobrança já foi paga");
      case CANCELED -> throw new BusinessException("Cobrança está cancelada");
      default -> {
      }
    }
  }

  private void updateChargeWithPixData(Charge charge, PixPaymentDTO pixPayment) {
    charge.setPixCode(pixPayment.getQrCode());
    charge.setPaymentLink(pixPayment.getPaymentLink());

    chargeRepository.save(charge);
  }
}
