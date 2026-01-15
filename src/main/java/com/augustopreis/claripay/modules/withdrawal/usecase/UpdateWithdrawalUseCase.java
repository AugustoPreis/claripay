package com.augustopreis.claripay.modules.withdrawal.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.withdrawal.dto.UpdateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.mapper.WithdrawalMapper;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateWithdrawalUseCase {

  private final WithdrawalRepository withdrawalRepository;
  private final WithdrawalMapper withdrawalMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public WithdrawalDTO execute(Long id, UpdateWithdrawalDTO request) {
    Long userId = auth.getAuthenticatedId();

    Withdrawal withdrawal = withdrawalRepository
        .findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Retirada não encontrada"));

    withdrawalMapper.updateWithdrawalFromDTO(request, withdrawal);

    Withdrawal updated = withdrawalRepository.save(withdrawal);

    return withdrawalMapper.toDTO(updated);
  }
}
