package com.augustopreis.claripay.modules.withdrawal.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.withdrawal.dto.CreateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.UpdateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.usecase.CreateWithdrawalUseCase;
import com.augustopreis.claripay.modules.withdrawal.usecase.FindManyWithdrawalUseCase;
import com.augustopreis.claripay.modules.withdrawal.usecase.FindOneWithdrawalUseCase;
import com.augustopreis.claripay.modules.withdrawal.usecase.RemoveWithdrawalUseCase;
import com.augustopreis.claripay.modules.withdrawal.usecase.UpdateWithdrawalUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/withdrawals")
@RequiredArgsConstructor
public class WithdrawalController {

  private final FindManyWithdrawalUseCase findManyWithdrawal;
  private final FindOneWithdrawalUseCase findOneWithdrawal;
  private final CreateWithdrawalUseCase createWithdrawal;
  private final UpdateWithdrawalUseCase updateWithdrawal;
  private final RemoveWithdrawalUseCase removeWithdrawal;

  @GetMapping
  public ResponseEntity<ApiResponse<Page<WithdrawalDTO>>> findAll(
      @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<WithdrawalDTO> response = findManyWithdrawal.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Retiradas listadas com sucesso", response));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<WithdrawalDTO>> findOne(@PathVariable Long id) {
    WithdrawalDTO response = findOneWithdrawal.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Retirada encontrada com sucesso", response));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<WithdrawalDTO>> create(@Valid @RequestBody CreateWithdrawalDTO request) {
    WithdrawalDTO response = createWithdrawal.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Retirada criada com sucesso", response));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<WithdrawalDTO>> update(
      @PathVariable Long id,
      @Valid @RequestBody UpdateWithdrawalDTO request) {
    WithdrawalDTO response = updateWithdrawal.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Retirada atualizada com sucesso", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    removeWithdrawal.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Retirada removida com sucesso", null));
  }
}
