package com.augustopreis.claripay.modules.alert.usecase;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.modules.alert.constants.AlertMessages;
import com.augustopreis.claripay.modules.alert.enums.AlertSeverityEnum;
import com.augustopreis.claripay.modules.alert.enums.AlertTypeEnum;
import com.augustopreis.claripay.modules.alert.repository.AlertRepository;
import com.augustopreis.claripay.modules.alert.repository.entity.Alert;
import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateLateChargeAlertsUseCase {

  private final ChargeRepository chargeRepository;
  private final AlertRepository alertRepository;

  @Transactional
  public void execute() {
    List<Charge> lateCharges = findAllLateCharges();

    for (Charge charge : lateCharges) {
      if (shouldSkipAlert(charge)) {
        continue;
      }

      createLateChargeAlert(charge);
    }
  }

  private List<Charge> findAllLateCharges() {
    return chargeRepository.findAllByStatusAndActiveTrue(ChargeStatusEnum.LATE);
  }

  private boolean shouldSkipAlert(Charge charge) {
    return alertRepository
        .findByUserIdOrderByCreatedAtDesc(charge.getUser().getId(), null)
        .stream()
        .anyMatch(alert -> isUnreadLateChargeAlertForCustomer(alert, charge));
  }

  private boolean isUnreadLateChargeAlertForCustomer(Alert alert, Charge charge) {
    return alert.getType() == AlertTypeEnum.LATE_CHARGE
        && alert.getMessage().contains(charge.getCustomer().getName())
        && !alert.getRead();
  }

  private void createLateChargeAlert(Charge charge) {
    String message = buildAlertMessage(charge);

    Alert alert = Alert.builder()
        .title(AlertMessages.LATE_CHARGE_TITLE)
        .message(message)
        .type(AlertTypeEnum.LATE_CHARGE)
        .severity(AlertSeverityEnum.WARNING)
        .user(charge.getUser())
        .build();

    alertRepository.save(alert);
  }

  private String buildAlertMessage(Charge charge) {
    return AlertMessages.lateChargeMessage(
        charge.getAmount(),
        charge.getCustomer().getName(),
        charge.getDueDate());
  }
}
