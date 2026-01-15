package com.augustopreis.claripay.modules.withdrawal.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveWithdrawalUseCase {

  private final WithdrawalRepository withdrawalRepository;
  private final AuthenticationUtil auth;

  @Transactional
  public void execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Withdrawal withdrawal = withdrawalRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Retirada não encontrada"));

    withdrawalRepository.delete(withdrawal);
  }
}
