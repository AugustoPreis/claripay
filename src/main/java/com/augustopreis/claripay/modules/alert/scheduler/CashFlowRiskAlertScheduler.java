package com.augustopreis.claripay.modules.alert.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.alert.usecase.GenerateCashFlowRiskAlertsUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashFlowRiskAlertScheduler {

  private final GenerateCashFlowRiskAlertsUseCase generateCashFlowRiskAlerts;

  @Scheduled(cron = "0 0 10 * * *")
  public void generateCashFlowRiskAlerts() {
    generateCashFlowRiskAlerts.execute();
  }
}
