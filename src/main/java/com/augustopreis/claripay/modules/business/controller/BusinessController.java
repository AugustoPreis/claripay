package com.augustopreis.claripay.modules.business.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.dto.CreateBusinessDTO;
import com.augustopreis.claripay.modules.business.dto.UpdateBusinessDTO;
import com.augustopreis.claripay.modules.business.usecase.CreateBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.FindManyBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.FindOneBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.RemoveBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.UpdateBusinessUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {

  final FindManyBusinessUseCase findManyBusiness;
  final FindOneBusinessUseCase findOneBusiness;
  final CreateBusinessUseCase createBusiness;
  final UpdateBusinessUseCase updateBusiness;
  final RemoveBusinessUseCase removeBusiness;

  @GetMapping
  public ResponseEntity<ApiResponse<Page<BusinessDTO>>> findAll(@PageableDefault(size = 10) Pageable pageable) {
    Page<BusinessDTO> response = findManyBusiness.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócios listados com sucesso", response));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<BusinessDTO>> findOne(@PathVariable Long id) {
    BusinessDTO response = findOneBusiness.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio encontrado com sucesso", response));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<BusinessDTO>> create(@Valid @RequestBody CreateBusinessDTO request) {
    BusinessDTO response = createBusiness.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Negócio registrado com sucesso", response));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<BusinessDTO>> update(
      @PathVariable Long id,
      @Valid @RequestBody UpdateBusinessDTO request) {
    BusinessDTO response = updateBusiness.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    removeBusiness.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio removido com sucesso", null));
  }
}
