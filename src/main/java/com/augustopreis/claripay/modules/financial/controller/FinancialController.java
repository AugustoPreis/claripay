package com.augustopreis.claripay.modules.financial.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.financial.dto.FinancialSummaryDTO;
import com.augustopreis.claripay.modules.financial.usecase.GetFinancialSummaryUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
@Tag(name = "Financeiro", description = "Relatórios financeiros")
public class FinancialController {

  private final GetFinancialSummaryUseCase getFinancialSummary;

  @GetMapping("/summary")
  @Operation(
    summary = "Resumo financeiro",
    description = "Retorna resumo com receitas, despesas e saldo em um período"
  )
  @ApiOk(schema = FinancialSummaryDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<FinancialSummaryDTO>> getSummary(
      @Parameter(description = "Data inicial", example = "2024-01-01")
      @RequestParam(required = false) LocalDate startDate,
      @Parameter(description = "Data final", example = "2024-12-31")
      @RequestParam(required = false) LocalDate endDate) {
    FinancialSummaryDTO response = getFinancialSummary.execute(startDate, endDate);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Resumo financeiro obtido com sucesso", response));
  }
}
