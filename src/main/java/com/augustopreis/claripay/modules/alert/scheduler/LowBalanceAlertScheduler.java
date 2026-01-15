package com.augustopreis.claripay.modules.alert.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.alert.usecase.GenerateLowBalanceAlertsUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LowBalanceAlertScheduler {

  private final GenerateLowBalanceAlertsUseCase generateLowBalanceAlerts;

  @Scheduled(cron = "0 0 8 * * *")
  public void generateLowBalanceAlerts() {
    generateLowBalanceAlerts.execute();
  }
}
