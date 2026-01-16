package com.augustopreis.claripay.modules.customer.controller;

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
import com.augustopreis.claripay.modules.customer.dto.CreateCustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.CustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.UpdateCustomerDTO;
import com.augustopreis.claripay.modules.customer.usecase.CreateCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.FindManyCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.FindOneCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.RemoveCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.UpdateCustomerUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class CustomerController {

  final FindManyCustomerUseCase findManyCustomer;
  final FindOneCustomerUseCase findOneCustomer;
  final CreateCustomerUseCase createCustomer;
  final UpdateCustomerUseCase updateCustomer;
  final RemoveCustomerUseCase removeCustomer;

  @GetMapping
  @Operation(summary = "Listar clientes", description = "Lista todos os clientes com paginação")
  @ApiOk(schema = CustomerDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Page<CustomerDTO>>> findAll(
      @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
    Page<CustomerDTO> response = findManyCustomer.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Clientes listados com sucesso", response));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar cliente", description = "Busca um cliente específico por ID")
  @ApiOk(schema = CustomerDTO.class)
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<CustomerDTO>> findOne(
      @Parameter(description = "ID do cliente", example = "1") @PathVariable Long id) {
    CustomerDTO response = findOneCustomer.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente encontrado com sucesso", response));
  }

  @PostMapping
  @Operation(summary = "Criar cliente", description = "Cria um novo cliente")
  @ApiCreated(schema = CustomerDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<CustomerDTO>> create(@Valid @RequestBody CreateCustomerDTO request) {
    CustomerDTO response = createCustomer.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Cliente registrado com sucesso", response));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Atualizar cliente", description = "Atualiza dados de um cliente existente")
  @ApiOk(schema = CustomerDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<CustomerDTO>> update(
      @Parameter(description = "ID do cliente", example = "1") @PathVariable Long id,
      @Valid @RequestBody UpdateCustomerDTO request) {
    CustomerDTO response = updateCustomer.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Remover cliente", description = "Remove um cliente do sistema")
  @ApiOk
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> delete(
      @Parameter(description = "ID do cliente", example = "1") @PathVariable Long id) {
    removeCustomer.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente removido com sucesso", null));
  }
}
