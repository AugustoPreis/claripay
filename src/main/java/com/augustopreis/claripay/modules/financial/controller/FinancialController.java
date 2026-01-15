package com.augustopreis.claripay.modules.financial.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.financial.dto.FinancialSummaryDTO;
import com.augustopreis.claripay.modules.financial.usecase.GetFinancialSummaryUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialController {

  private final GetFinancialSummaryUseCase getFinancialSummary;

  @GetMapping("/summary")
  public ResponseEntity<ApiResponse<FinancialSummaryDTO>> getSummary(
      @RequestParam(required = false) LocalDate startDate,
      @RequestParam(required = false) LocalDate endDate) {
    FinancialSummaryDTO response = getFinancialSummary.execute(startDate, endDate);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Resumo financeiro obtido com sucesso", response));
  }
}
