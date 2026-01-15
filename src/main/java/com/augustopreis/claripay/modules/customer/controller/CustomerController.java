package com.augustopreis.claripay.modules.customer.controller;

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
import com.augustopreis.claripay.modules.customer.dto.CreateCustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.CustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.UpdateCustomerDTO;
import com.augustopreis.claripay.modules.customer.usecase.CreateCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.FindManyCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.FindOneCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.RemoveCustomerUseCase;
import com.augustopreis.claripay.modules.customer.usecase.UpdateCustomerUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  final FindManyCustomerUseCase findManyCustomer;
  final FindOneCustomerUseCase findOneCustomer;
  final CreateCustomerUseCase createCustomer;
  final UpdateCustomerUseCase updateCustomer;
  final RemoveCustomerUseCase removeCustomer;

  @GetMapping
  public ResponseEntity<ApiResponse<Page<CustomerDTO>>> findAll(@PageableDefault(size = 10) Pageable pageable) {
    Page<CustomerDTO> response = findManyCustomer.execute(pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Clientes listados com sucesso", response));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerDTO>> findOne(@PathVariable Long id) {
    CustomerDTO response = findOneCustomer.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente encontrado com sucesso", response));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CustomerDTO>> create(@Valid @RequestBody CreateCustomerDTO request) {
    CustomerDTO response = createCustomer.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Cliente registrado com sucesso", response));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerDTO>> update(
      @PathVariable Long id,
      @Valid @RequestBody UpdateCustomerDTO request) {
    CustomerDTO response = updateCustomer.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente atualizado com sucesso", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    removeCustomer.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Cliente removido com sucesso", null));
  }
}
