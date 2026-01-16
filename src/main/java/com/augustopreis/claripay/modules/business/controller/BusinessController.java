package com.augustopreis.claripay.modules.business.controller;

import org.springdoc.core.annotations.ParameterObject;
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
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiCreated;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.dto.CreateBusinessDTO;
import com.augustopreis.claripay.modules.business.dto.UpdateBusinessDTO;
import com.augustopreis.claripay.modules.business.usecase.CreateBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.FindManyBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.FindOneBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.RemoveBusinessUseCase;
import com.augustopreis.claripay.modules.business.usecase.UpdateBusinessUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
@Tag(name = "Negócios", description = "Gerenciamento de negócios/empresas")
public class BusinessController {

  final FindManyBusinessUseCase findManyBusiness;
  final FindOneBusinessUseCase findOneBusiness;
  final CreateBusinessUseCase createBusiness;
  final UpdateBusinessUseCase updateBusiness;
  final RemoveBusinessUseCase removeBusiness;

  @GetMapping
  @Operation(summary = "Listar negócios", description = "Lista todos os negócios do usuário com paginação")
  @ApiOk(description = "Negócios listados", schema = BusinessDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Page<BusinessDTO>>> findAll(
      @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
    Page<BusinessDTO> response = findManyBusiness.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócios listados com sucesso", response));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar negócio", description = "Busca um negócio específico por ID")
  @ApiOk(schema = BusinessDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<BusinessDTO>> findOne(
      @Parameter(description = "ID do negócio", example = "1") @PathVariable Long id) {
    BusinessDTO response = findOneBusiness.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio encontrado com sucesso", response));
  }

  @PostMapping
  @Operation(summary = "Criar negócio", description = "Cria um novo negócio")
  @ApiCreated(schema = BusinessDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<BusinessDTO>> create(@Valid @RequestBody CreateBusinessDTO request) {
    BusinessDTO response = createBusiness.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Negócio registrado com sucesso", response));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Atualizar negócio", description = "Atualiza dados de um negócio existente")
  @ApiOk(schema = BusinessDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<BusinessDTO>> update(
      @Parameter(description = "ID do negócio", example = "1") @PathVariable Long id,
      @Valid @RequestBody UpdateBusinessDTO request) {
    BusinessDTO response = updateBusiness.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remover negócio", description = "Remove um negócio do sistema")
  @ApiOk
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> delete(
      @Parameter(description = "ID do negócio", example = "1") @PathVariable Long id) {
    removeBusiness.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Negócio removido com sucesso", null));
  }
}
