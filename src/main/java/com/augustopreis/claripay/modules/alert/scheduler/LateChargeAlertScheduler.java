package com.augustopreis.claripay.modules.alert.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.alert.usecase.GenerateLateChargeAlertsUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LateChargeAlertScheduler {

  private final GenerateLateChargeAlertsUseCase generateLateChargeAlerts;

  @Scheduled(cron = "0 0 9 * * *")
  public void generateLateChargeAlerts() {
    generateLateChargeAlerts.execute();
  }
}
