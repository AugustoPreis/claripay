package com.augustopreis.claripay.modules.expense.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.expense.dto.ExpenseDTO;
import com.augustopreis.claripay.modules.expense.mapper.ExpenseMapper;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyExpenseUseCase {

  private final ExpenseRepository expenseRepository;
  private final ExpenseMapper expenseMapper;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public Page<ExpenseDTO> execute(Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    return expenseRepository
        .findAllByUserId(userId, pageable)
        .map(expenseMapper::toDTO);
  }
}
