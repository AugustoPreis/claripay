package com.augustopreis.claripay.modules.charge.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.mapper.ChargeMapper;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindOneChargeUseCase {

  private final ChargeRepository chargeRepository;
  private final ChargeMapper chargeMapper;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public ChargeDTO execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Charge charge = chargeRepository
        .findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cobrança não encontrada"));

    return chargeMapper.toDTO(charge);
  }
}
