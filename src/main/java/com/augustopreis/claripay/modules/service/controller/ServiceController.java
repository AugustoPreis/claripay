package com.augustopreis.claripay.modules.service.controller;

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
import com.augustopreis.claripay.modules.service.dto.CreateServiceDTO;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.dto.UpdateServiceDTO;
import com.augustopreis.claripay.modules.service.usecase.CreateServiceUseCase;
import com.augustopreis.claripay.modules.service.usecase.FindManyServiceUseCase;
import com.augustopreis.claripay.modules.service.usecase.FindOneServiceUseCase;
import com.augustopreis.claripay.modules.service.usecase.RemoveServiceUseCase;
import com.augustopreis.claripay.modules.service.usecase.UpdateServiceUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento de serviços")
public class ServiceController {

  final FindManyServiceUseCase findManyService;
  final FindOneServiceUseCase findOneService;
  final CreateServiceUseCase createService;
  final UpdateServiceUseCase updateService;
  final RemoveServiceUseCase removeService;

  @GetMapping
  @Operation(summary = "Listar serviços", description = "Lista todos os serviços com paginação")
  @ApiOk(schema = ServiceDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Page<ServiceDTO>>> findAll(
      @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
    Page<ServiceDTO> response = findManyService.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Serviços listados com sucesso", response));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar serviço", description = "Busca um serviço específico por ID")
  @ApiOk(schema = ServiceDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ServiceDTO>> findOne(
      @Parameter(description = "ID do serviço", example = "1") @PathVariable Long id) {
    ServiceDTO response = findOneService.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Serviço encontrado com sucesso", response));
  }

  @PostMapping
  @Operation(summary = "Criar serviço", description = "Cria um novo serviço")
  @ApiCreated(schema = ServiceDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ServiceDTO>> create(@Valid @RequestBody CreateServiceDTO request) {
    ServiceDTO response = createService.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Serviço registrado com sucesso", response));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Atualizar serviço", description = "Atualiza dados de um serviço existente")
  @ApiOk(schema = ServiceDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ServiceDTO>> update(
      @Parameter(description = "ID do serviço", example = "1") @PathVariable Long id,
      @Valid @RequestBody UpdateServiceDTO request) {
    ServiceDTO response = updateService.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Serviço atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remover serviço", description = "Remove um serviço do sistema")
  @ApiOk
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> delete(
      @Parameter(description = "ID do serviço", example = "1") @PathVariable Long id) {
    removeService.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Serviço removido com sucesso", null));
  }
}
