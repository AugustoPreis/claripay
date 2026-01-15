package com.augustopreis.claripay.modules.financial.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.financial.dto.FinancialSummaryDTO;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetFinancialSummaryUseCase {

  private final ChargeRepository chargeRepository;
  private final ExpenseRepository expenseRepository;
  private final WithdrawalRepository withdrawalRepository;
  private final AuthenticationUtil auth;

  public FinancialSummaryDTO execute(LocalDate startDate, LocalDate endDate) {
    Long userId = auth.getAuthenticatedId();

    if (startDate == null) {
      startDate = LocalDate.now().withDayOfMonth(1);
    }

    if (endDate == null) {
      endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    }

    BigDecimal totalReceived = chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.PAID, startDate, endDate);

    BigDecimal totalPending = chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.PENDING, startDate, endDate);

    BigDecimal totalOverdue = chargeRepository
        .sumByUserIdAndStatusAndDueDateBetween(userId, ChargeStatusEnum.LATE, startDate, endDate);

    BigDecimal businessExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.BUSINESS, startDate, endDate);

    BigDecimal personalExpenses = expenseRepository
        .sumByUserIdAndTypeAndDateBetween(userId, ExpenseTypeEnum.PERSONAL, startDate, endDate);

    BigDecimal withdrawals = withdrawalRepository
        .sumByUserIdAndDateBetween(userId, startDate, endDate);

    BigDecimal businessBalance = totalReceived
        .subtract(businessExpenses)
        .subtract(withdrawals);

    BigDecimal personalBalance = withdrawals.subtract(personalExpenses);

    BigDecimal totalRevenue = totalReceived;
    BigDecimal totalExpenses = businessExpenses.add(personalExpenses);
    BigDecimal netProfit = totalRevenue.subtract(totalExpenses);

    return FinancialSummaryDTO.builder()
        .totalReceived(totalReceived)
        .totalPending(totalPending)
        .totalOverdue(totalOverdue)
        .businessExpenses(businessExpenses)
        .withdrawals(withdrawals)
        .businessBalance(businessBalance)
        .personalBalance(personalBalance)
        .personalExpenses(personalExpenses)
        .totalRevenue(totalRevenue)
        .totalExpenses(totalExpenses)
        .netProfit(netProfit)
        .build();
  }
}
