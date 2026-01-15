package com.augustopreis.claripay.modules.charge.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChargeStatusScheduler {

  private final ChargeRepository chargeRepository;

  @Scheduled(cron = "0 0 0 * * *")
  @Transactional
  public void updateOverdueCharges() {
    LocalDate today = LocalDate.now();

    List<Charge> overdueCharges = chargeRepository
        .findByDueDateBeforeAndStatusAndActiveTrue(today, ChargeStatusEnum.PENDING);

    if (overdueCharges.isEmpty()) {
      return;
    }

    overdueCharges.forEach(charge -> charge.setStatus(ChargeStatusEnum.LATE));

    chargeRepository.saveAll(overdueCharges);
  }
}
