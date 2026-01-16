package com.augustopreis.claripay.modules.expense.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiCreated;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.expense.dto.CreateExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.ExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.UpdateExpenseDTO;
import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.usecase.CreateExpenseUseCase;
import com.augustopreis.claripay.modules.expense.usecase.FindManyExpenseUseCase;
import com.augustopreis.claripay.modules.expense.usecase.FindOneExpenseUseCase;
import com.augustopreis.claripay.modules.expense.usecase.RemoveExpenseUseCase;
import com.augustopreis.claripay.modules.expense.usecase.UpdateExpenseUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Despesas", description = "Gerenciamento de despesas")
public class ExpenseController {

  final FindManyExpenseUseCase findManyExpense;
  final FindOneExpenseUseCase findOneExpense;
  final CreateExpenseUseCase createExpense;
  final UpdateExpenseUseCase updateExpense;
  final RemoveExpenseUseCase removeExpense;

  @GetMapping
  @Operation(
    summary = "Listar despesas",
    description = "Lista despesas com filtro de tipo e paginação"
  )
  @ApiOk(schema = Page.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Page<ExpenseDTO>>> findAll(
      @Parameter(description = "Tipo da despesa")
      @RequestParam(required = false) ExpenseTypeEnum type,
      @PageableDefault(size = 10) Pageable pageable) {
    Page<ExpenseDTO> response = findManyExpense.execute(type, pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Despesas listadas com sucesso", response));
  }

  @GetMapping("/{id}")
  @Operation(
    summary = "Buscar despesa",
    description = "Retorna uma despesa por ID"
  )
  @ApiOk(schema = ExpenseDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ExpenseDTO>> findOne(
      @Parameter(description = "ID da despesa", example = "1")
      @PathVariable Long id) {
    ExpenseDTO response = findOneExpense.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Despesa encontrada com sucesso", response));
  }

  @PostMapping
  @Operation(
    summary = "Criar despesa",
    description = "Cria uma nova despesa"
  )
  @ApiCreated(schema = ExpenseDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ExpenseDTO>> create(@Valid @RequestBody CreateExpenseDTO request) {
    ExpenseDTO response = createExpense.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Despesa registrada com sucesso", response));
  }

  @PatchMapping("/{id}")
  @Operation(
    summary = "Atualizar despesa",
    description = "Atualiza uma despesa existente"
  )
  @ApiOk(schema = ExpenseDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<ExpenseDTO>> update(
      @Parameter(description = "ID da despesa", example = "1")
      @PathVariable Long id,
      @Valid @RequestBody UpdateExpenseDTO request) {
    ExpenseDTO response = updateExpense.execute(id, request);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Despesa atualizada com sucesso", response));
  }

  @DeleteMapping("/{id}")
  @Operation(
    summary = "Remover despesa",
    description = "Remove uma despesa"
  )
  @ApiOk
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> delete(
      @Parameter(description = "ID da despesa", example = "1")
      @PathVariable Long id) {
    removeExpense.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Despesa removida com sucesso", null));
  }
}
