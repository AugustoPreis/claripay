package com.augustopreis.claripay.modules.charge.controller;

import java.math.BigDecimal;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.CreateChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.UpdateChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.UpdateChargeStatusDTO;
import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.usecase.CreateChargeUseCase;
import com.augustopreis.claripay.modules.charge.usecase.FindManyChargeUseCase;
import com.augustopreis.claripay.modules.charge.usecase.FindOneChargeUseCase;
import com.augustopreis.claripay.modules.charge.usecase.RemoveChargeUseCase;
import com.augustopreis.claripay.modules.charge.usecase.UpdateChargeStatusUseCase;
import com.augustopreis.claripay.modules.charge.usecase.UpdateChargeUseCase;
import com.augustopreis.claripay.modules.payment.dto.PixPaymentDTO;
import com.augustopreis.claripay.modules.payment.usecase.GenerateChargePixUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeController {

  private final FindManyChargeUseCase findManyCharge;
  private final FindOneChargeUseCase findOneCharge;
  private final CreateChargeUseCase createCharge;
  private final UpdateChargeUseCase updateCharge;
  private final UpdateChargeStatusUseCase updateChargeStatus;
  private final RemoveChargeUseCase removeCharge;
  private final GenerateChargePixUseCase generateChargePix;

  @GetMapping
  public ResponseEntity<ApiResponse<Page<ChargeDTO>>> findAll(
      @RequestParam(required = false) Long customerId,
      @RequestParam(required = false) BigDecimal minAmount,
      @RequestParam(required = false) BigDecimal maxAmount,
      @RequestParam(required = false) ChargeStatusEnum status,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<ChargeDTO> response = findManyCharge.execute(customerId, minAmount, maxAmount, status, pageable);
    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobranças listadas com sucesso", response));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ChargeDTO>> findOne(@PathVariable Long id) {
    ChargeDTO response = findOneCharge.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança encontrada com sucesso", response));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<ChargeDTO>> create(@Valid @RequestBody CreateChargeDTO request) {
    ChargeDTO response = createCharge.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Cobrança criada com sucesso", response));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<ChargeDTO>> update(
      @PathVariable Long id,
      @Valid @RequestBody UpdateChargeDTO request) {
    ChargeDTO response = updateCharge.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança atualizada com sucesso", response));
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<ApiResponse<ChargeDTO>> updateStatus(
      @PathVariable Long id,
      @Valid @RequestBody UpdateChargeStatusDTO request) {
    ChargeDTO response = updateChargeStatus.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Status da cobrança atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    removeCharge.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança removida com sucesso", null));
  }

  @PostMapping("/{id}/generate-pix")
  public ResponseEntity<ApiResponse<PixPaymentDTO>> generatePix(@PathVariable Long id) {
    PixPaymentDTO response = generateChargePix.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Pix gerado com sucesso", response));
  }
}
