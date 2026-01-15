package com.augustopreis.claripay.modules.financial.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialSummaryDTO {
  private BigDecimal totalReceived;
  private BigDecimal totalPending;
  private BigDecimal totalOverdue;
  private BigDecimal businessExpenses;
  private BigDecimal withdrawals;
  private BigDecimal businessBalance;
  private BigDecimal personalBalance;
  private BigDecimal personalExpenses;
  private BigDecimal totalRevenue;
  private BigDecimal totalExpenses;
  private BigDecimal netProfit;
}
