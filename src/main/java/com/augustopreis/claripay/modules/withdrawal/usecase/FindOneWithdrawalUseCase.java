package com.augustopreis.claripay.modules.withdrawal.usecase;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.mapper.WithdrawalMapper;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindOneWithdrawalUseCase {

  private final WithdrawalRepository withdrawalRepository;
  private final WithdrawalMapper withdrawalMapper;
  private final AuthenticationUtil auth;

  public WithdrawalDTO execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Withdrawal withdrawal = withdrawalRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Retirada não encontrada"));

    return withdrawalMapper.toDTO(withdrawal);
  }
}
