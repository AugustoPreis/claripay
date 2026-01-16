package com.augustopreis.claripay.modules.financial.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resumo financeiro")
public class FinancialSummaryDTO {
  
  @Schema(description = "Total recebido", example = "15000.00")
  private BigDecimal totalReceived;
  
  @Schema(description = "Total pendente", example = "5000.00")
  private BigDecimal totalPending;
  
  @Schema(description = "Total vencido", example = "1000.00")
  private BigDecimal totalOverdue;
  
  @Schema(description = "Despesas do negócio", example = "8000.00")
  private BigDecimal businessExpenses;
  
  @Schema(description = "Retiradas", example = "3000.00")
  private BigDecimal withdrawals;
  
  @Schema(description = "Saldo do negócio", example = "4000.00")
  private BigDecimal businessBalance;
  
  @Schema(description = "Saldo pessoal", example = "2000.00")
  private BigDecimal personalBalance;
  
  @Schema(description = "Despesas pessoais", example = "1000.00")
  private BigDecimal personalExpenses;
  
  @Schema(description = "Receita total", example = "20000.00")
  private BigDecimal totalRevenue;
  
  @Schema(description = "Despesas totais", example = "9000.00")
  private BigDecimal totalExpenses;
  
  @Schema(description = "Lucro líquido", example = "11000.00")
  private BigDecimal netProfit;
}
