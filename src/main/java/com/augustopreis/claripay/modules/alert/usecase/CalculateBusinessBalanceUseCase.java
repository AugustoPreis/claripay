package com.augustopreis.claripay.modules.alert.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CalculateBusinessBalanceUseCase {

  private final ChargeRepository chargeRepository;
  private final ExpenseRepository expenseRepository;
  private final WithdrawalRepository withdrawalRepository;

  @Transactional(readOnly = true)
  public BalanceResult execute(Long userId) {
    LocalDate startDate = LocalDate.of(2000, 1, 1);
    LocalDate today = LocalDate.now();
    LocalDate futureDate = today.plusDays(7);

    BigDecimal totalReceived = chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.PAID, startDate, today);

    BigDecimal businessExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, startDate, today);

    BigDecimal withdrawals = withdrawalRepository
        .sumByUserIdAndDateBetween(userId, startDate, today);

    BigDecimal currentBalance = totalReceived
        .subtract(businessExpenses)
        .subtract(withdrawals);

    BigDecimal upcomingExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, today, futureDate);

    return new BalanceResult(currentBalance, upcomingExpenses);
  }

  public record BalanceResult(BigDecimal currentBalance, BigDecimal upcomingExpenses) {
  }
}
