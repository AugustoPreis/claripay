package com.augustopreis.claripay.modules.alert.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateCashFlowRiskAlertsUseCase {

  private final UserRepository userRepository;
  private final AlertRepository alertRepository;
  private final ChargeRepository chargeRepository;
  private final ExpenseRepository expenseRepository;
  private final WithdrawalRepository withdrawalRepository;

  @Transactional
  public void execute() {
    List<User> users = userRepository.findAll();

    for (User user : users) {
      processCashFlowRiskAlert(user);
    }
  }

  private void processCashFlowRiskAlert(User user) {
    if (hasUnreadCashFlowRiskAlert(user)) {
      return;
    }

    BigDecimal currentBalance = calculateCurrentBusinessBalance(user.getId());
    BigDecimal upcomingExpenses = calculateUpcomingExpenses(user.getId());
    BigDecimal expectedIncome = calculateExpectedIncome(user.getId());

    if (hasCashFlowRisk(currentBalance, upcomingExpenses, expectedIncome)) {
      createCashFlowRiskAlert(user, currentBalance, upcomingExpenses);
    }
  }

  private boolean hasUnreadCashFlowRiskAlert(User user) {
    return alertRepository
        .findByUserIdAndReadOrderByCreatedAtDesc(user.getId(), false, null)
        .stream()
        .anyMatch(alert -> alert.getType() == AlertTypeEnum.CASH_FLOW_RISK);
  }

  private BigDecimal calculateCurrentBusinessBalance(Long userId) {
    LocalDate startDate = LocalDate.of(2000, 1, 1);
    LocalDate today = LocalDate.now();

    BigDecimal totalReceived = chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.PAID, startDate, today);

    BigDecimal businessExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, startDate, today);

    BigDecimal withdrawals = withdrawalRepository
        .sumByUserIdAndDateBetween(userId, startDate, today);

    return totalReceived
        .subtract(businessExpenses)
        .subtract(withdrawals);
  }

  private BigDecimal calculateUpcomingExpenses(Long userId) {
    LocalDate today = LocalDate.now();
    LocalDate futureDate = today.plusDays(7);

    return expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, today, futureDate);
  }

  private BigDecimal calculateExpectedIncome(Long userId) {
    LocalDate today = LocalDate.now();
    LocalDate futureDate = today.plusDays(7);

    return chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.PENDING, today, futureDate);
  }

  private boolean hasCashFlowRisk(BigDecimal currentBalance, BigDecimal upcomingExpenses,
      BigDecimal expectedIncome) {
    if (upcomingExpenses.compareTo(BigDecimal.ZERO) <= 0) {
      return false;
    }

    BigDecimal projectedBalance = currentBalance.add(expectedIncome);

    return projectedBalance.compareTo(upcomingExpenses) < 0;
  }

  private void createCashFlowRiskAlert(User user, BigDecimal currentBalance, BigDecimal upcomingExpenses) {
    String message = AlertMessages.cashFlowRiskMessage(currentBalance, upcomingExpenses);

    Alert alert = Alert.builder()
        .title(AlertMessages.CASH_FLOW_RISK_TITLE)
        .message(message)
        .type(AlertTypeEnum.CASH_FLOW_RISK)
        .severity(AlertSeverityEnum.WARNING)
        .user(user)
        .build();

    alertRepository.save(alert);
  }
}
