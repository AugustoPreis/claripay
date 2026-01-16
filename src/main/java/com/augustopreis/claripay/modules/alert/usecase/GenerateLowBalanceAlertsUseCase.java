package com.augustopreis.claripay.modules.alert.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.modules.alert.constants.AlertMessages;
import com.augustopreis.claripay.modules.alert.enums.AlertSeverityEnum;
import com.augustopreis.claripay.modules.alert.enums.AlertTypeEnum;
import com.augustopreis.claripay.modules.alert.repository.AlertRepository;
import com.augustopreis.claripay.modules.alert.repository.entity.Alert;
import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenerateLowBalanceAlertsUseCase {

  private final UserRepository userRepository;
  private final AlertRepository alertRepository;
  private final ExpenseRepository expenseRepository;
  private final CalculateBusinessBalanceUseCase calculateBusinessBalance;

  @Transactional
  public void execute() {
    List<User> users = userRepository.findAll();

    for (User user : users) {
      processUserBalanceAlert(user);
    }
  }

  private void processUserBalanceAlert(User user) {
    if (hasUnreadBalanceAlert(user)) {
      return;
    }

    CalculateBusinessBalanceUseCase.BalanceResult balanceResult = calculateBusinessBalance.execute(user.getId());
    BigDecimal currentBalance = balanceResult.currentBalance();
    BigDecimal upcomingExpenses = balanceResult.upcomingExpenses();
    BigDecimal lowBalanceThreshold = calculateLowBalanceThreshold(user.getId());

    createAppropriateAlert(user, currentBalance, upcomingExpenses, lowBalanceThreshold);
  }

  private boolean hasUnreadBalanceAlert(User user) {
    return alertRepository
        .findByUserIdAndReadOrderByCreatedAtDesc(user.getId(), false, null)
        .stream()
        .anyMatch(this::isBalanceAlert);
  }

  private boolean isBalanceAlert(Alert alert) {
    final AlertTypeEnum type = alert.getType();

    return type == AlertTypeEnum.LOW_BALANCE
        || type == AlertTypeEnum.NEGATIVE_BALANCE
        || type == AlertTypeEnum.CASH_FLOW_RISK;
  }

  private BigDecimal calculateLowBalanceThreshold(Long userId) {
    LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
    LocalDate today = LocalDate.now();

    BigDecimal threeMonthExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, threeMonthsAgo, today);

    BigDecimal monthlyAverageExpenses = threeMonthExpenses
        .divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);

    return monthlyAverageExpenses.multiply(BigDecimal.valueOf(0.20));
  }

  private void createAppropriateAlert(User user, BigDecimal currentBalance,
      BigDecimal upcomingExpenses, BigDecimal lowBalanceThreshold) {

    if (isNegativeBalance(currentBalance)) {
      createNegativeBalanceAlert(user, currentBalance);
    } else if (isInsufficientForUpcomingExpenses(currentBalance, upcomingExpenses)) {
      createInsufficientBalanceAlert(user, currentBalance, upcomingExpenses);
    } else if (isBelowThreshold(currentBalance, lowBalanceThreshold)) {
      createLowBalanceAlert(user, currentBalance);
    }
  }

  private boolean isNegativeBalance(BigDecimal currentBalance) {
    return currentBalance.compareTo(BigDecimal.ZERO) < 0;
  }

  private boolean isInsufficientForUpcomingExpenses(BigDecimal currentBalance, BigDecimal upcomingExpenses) {
    return upcomingExpenses.compareTo(BigDecimal.ZERO) > 0
        && currentBalance.compareTo(upcomingExpenses) < 0;
  }

  private boolean isBelowThreshold(BigDecimal currentBalance, BigDecimal threshold) {
    return threshold.compareTo(BigDecimal.ZERO) > 0
        && currentBalance.compareTo(threshold) < 0
        && currentBalance.compareTo(BigDecimal.ZERO) >= 0;
  }

  private void createNegativeBalanceAlert(User user, BigDecimal currentBalance) {
    String message = AlertMessages.negativeBalanceMessage(currentBalance);

    Alert alert = buildAlert(user, AlertMessages.NEGATIVE_BALANCE_TITLE, message,
        AlertTypeEnum.NEGATIVE_BALANCE, AlertSeverityEnum.CRITICAL);

    alertRepository.save(alert);
  }

  private void createInsufficientBalanceAlert(User user, BigDecimal currentBalance,
      BigDecimal upcomingExpenses) {
    String message = AlertMessages.cashFlowRiskMessage(currentBalance, upcomingExpenses);

    Alert alert = buildAlert(user, AlertMessages.CASH_FLOW_RISK_TITLE, message,
        AlertTypeEnum.CASH_FLOW_RISK, AlertSeverityEnum.WARNING);

    alertRepository.save(alert);
  }

  private void createLowBalanceAlert(User user, BigDecimal currentBalance) {
    String message = AlertMessages.lowBalanceMessage(currentBalance);

    Alert alert = buildAlert(user, AlertMessages.LOW_BALANCE_TITLE, message,
        AlertTypeEnum.LOW_BALANCE, AlertSeverityEnum.INFO);

    alertRepository.save(alert);
  }

  private Alert buildAlert(User user, String title, String message,
      AlertTypeEnum type, AlertSeverityEnum severity) {
    return Alert.builder()
        .title(title)
        .message(message)
        .type(type)
        .severity(severity)
        .user(user)
        .build();
  }
}
