package com.augustopreis.claripay.modules.alert.constants;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class AlertMessages {

  public static final String LATE_CHARGE_TITLE = "Cobrança Atrasada";
  public static final String NEGATIVE_BALANCE_TITLE = "Saldo Negativo";
  public static final String CASH_FLOW_RISK_TITLE = "Risco de Falta de Caixa";
  public static final String LOW_BALANCE_TITLE = "Saldo Baixo";

  public static String lateChargeMessage(BigDecimal amount, String customerName, LocalDate dueDate) {
    return String.format(
        "Você tem uma cobrança atrasada de R$ %.2f do cliente %s. Vencimento: %s",
        amount,
        customerName,
        dueDate);
  }

  public static String negativeBalanceMessage(BigDecimal currentBalance) {
    return String.format(
        "Atenção! Seu saldo está negativo: R$ %.2f. Você precisa regularizar urgentemente.",
        currentBalance);
  }

  public static String cashFlowRiskMessage(BigDecimal currentBalance, BigDecimal upcomingExpenses) {
    return String.format(
        "Atenção! Seu saldo atual (R$ %.2f) pode não ser suficiente para cobrir as despesas previstas dos próximos 7 dias (R$ %.2f). Considere adiar gastos ou buscar recursos adicionais.",
        currentBalance,
        upcomingExpenses);
  }

  public static String lowBalanceMessage(BigDecimal currentBalance) {
    return String.format(
        "Seu saldo está baixo: R$ %.2f. Recomendamos atenção ao fluxo de caixa dos próximos dias.",
        currentBalance);
  }
}
