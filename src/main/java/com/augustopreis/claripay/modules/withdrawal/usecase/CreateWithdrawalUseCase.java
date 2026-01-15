package com.augustopreis.claripay.modules.withdrawal.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.withdrawal.dto.CreateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.mapper.WithdrawalMapper;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateWithdrawalUseCase {

  private final WithdrawalRepository withdrawalRepository;
  private final WithdrawalMapper withdrawalMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public WithdrawalDTO execute(CreateWithdrawalDTO request) {
    Long userId = auth.getAuthenticatedId();

    Withdrawal withdrawal = withdrawalMapper.toEntity(request);
    withdrawal.setUser(User.builder().id(userId).build());

    Withdrawal saved = withdrawalRepository.save(withdrawal);

    return withdrawalMapper.toDTO(saved);
  }
}
