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
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiCreated;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
@Tag(name = "Cobranças", description = "Gerenciamento de cobranças")
public class ChargeController {

  private final FindManyChargeUseCase findManyCharge;
  private final FindOneChargeUseCase findOneCharge;
  private final CreateChargeUseCase createCharge;
  private final UpdateChargeUseCase updateCharge;
  private final UpdateChargeStatusUseCase updateChargeStatus;
  private final RemoveChargeUseCase removeCharge;
  private final GenerateChargePixUseCase generateChargePix;

  @GetMapping
  @Operation(summary = "Listar cobranças", description = "Lista cobranças com filtros e paginação")
  @ApiOk(schema = Page.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Page<ChargeDTO>>> findAll(
      @Parameter(description = "ID do cliente", example = "1") @RequestParam(required = false) Long customerId,
      @Parameter(description = "Valor mínimo", example = "100.00") @RequestParam(required = false) BigDecimal minAmount,
      @Parameter(description = "Valor máximo", example = "1000.00") @RequestParam(required = false) BigDecimal maxAmount,
      @Parameter(description = "Status da cobrança") @RequestParam(required = false) ChargeStatusEnum status,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<ChargeDTO> response = findManyCharge.execute(customerId, minAmount, maxAmount, status, pageable);
    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobranças listadas com sucesso", response));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar cobrança", description = "Retorna uma cobrança por ID")
  @ApiOk(schema = ChargeDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ChargeDTO>> findOne(
      @Parameter(description = "ID da cobrança", example = "1") @PathVariable Long id) {
    ChargeDTO response = findOneCharge.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança encontrada com sucesso", response));
  }

  @PostMapping
  @Operation(summary = "Criar cobrança", description = "Cria uma nova cobrança")
  @ApiCreated(schema = ChargeDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ChargeDTO>> create(@Valid @RequestBody CreateChargeDTO request) {
    ChargeDTO response = createCharge.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Cobrança criada com sucesso", response));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Atualizar cobrança", description = "Atualiza uma cobrança existente")
  @ApiOk(schema = ChargeDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ChargeDTO>> update(
      @Parameter(description = "ID da cobrança", example = "1") @PathVariable Long id,
      @Valid @RequestBody UpdateChargeDTO request) {
    ChargeDTO response = updateCharge.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança atualizada com sucesso", response));
  }

  @PatchMapping("/{id}/status")
  @Operation(summary = "Atualizar status da cobrança", description = "Atualiza o status de uma cobrança")
  @ApiOk(schema = ChargeDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ChargeDTO>> updateStatus(
      @Parameter(description = "ID da cobrança", example = "1") @PathVariable Long id,
      @Valid @RequestBody UpdateChargeStatusDTO request) {
    ChargeDTO response = updateChargeStatus.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Status da cobrança atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remover cobrança", description = "Remove uma cobrança")
  @ApiOk
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> delete(
      @Parameter(description = "ID da cobrança", example = "1") @PathVariable Long id) {
    removeCharge.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cobrança removida com sucesso", null));
  }

  @PostMapping("/{id}/generate-pix")
  @Operation(summary = "Gerar PIX", description = "Gera um código PIX para pagamento da cobrança")
  @ApiOk(schema = PixPaymentDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<PixPaymentDTO>> generatePix(
      @Parameter(description = "ID da cobrança", example = "1") @PathVariable Long id) {
    PixPaymentDTO response = generateChargePix.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Pix gerado com sucesso", response));
  }
}
