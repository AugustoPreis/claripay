package com.augustopreis.claripay.modules.expense.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.expense.dto.CreateExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.ExpenseDTO;
import com.augustopreis.claripay.modules.expense.mapper.ExpenseMapper;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateExpenseUseCase {

  private final ExpenseRepository expenseRepository;
  private final ExpenseMapper expenseMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ExpenseDTO execute(CreateExpenseDTO request) {
    Expense expense = expenseMapper.toEntity(request);

    expense.setUser(auth.getAuthenticatedUser());

    expense = expenseRepository.save(expense);

    return expenseMapper.toDTO(expense);
  }
}
