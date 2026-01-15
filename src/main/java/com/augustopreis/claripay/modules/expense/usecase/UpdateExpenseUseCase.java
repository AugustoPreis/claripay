package com.augustopreis.claripay.modules.expense.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.expense.dto.ExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.UpdateExpenseDTO;
import com.augustopreis.claripay.modules.expense.mapper.ExpenseMapper;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateExpenseUseCase {

  private final ExpenseRepository expenseRepository;
  private final ExpenseMapper expenseMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ExpenseDTO execute(Long id, UpdateExpenseDTO request) {
    Long userId = auth.getAuthenticatedId();

    Expense expense = expenseRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

    expenseMapper.updateExpenseFromDTO(request, expense);

    expense = expenseRepository.save(expense);

    return expenseMapper.toDTO(expense);
  }
}
