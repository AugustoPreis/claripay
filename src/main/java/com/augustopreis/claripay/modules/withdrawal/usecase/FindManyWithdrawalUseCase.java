package com.augustopreis.claripay.modules.withdrawal.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.mapper.WithdrawalMapper;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyWithdrawalUseCase {

  private final WithdrawalRepository withdrawalRepository;
  private final WithdrawalMapper withdrawalMapper;
  private final AuthenticationUtil auth;

  public Page<WithdrawalDTO> execute(Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    return withdrawalRepository
        .findByUserId(userId, pageable)
        .map(withdrawalMapper::toDTO);
  }
}
