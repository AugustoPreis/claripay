package com.augustopreis.claripay.modules.expense.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveExpenseUseCase {

  private final ExpenseRepository expenseRepository;
  private final AuthenticationUtil auth;

  @Transactional
  public void execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Expense expense = expenseRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

    expenseRepository.delete(expense);
  }
}
