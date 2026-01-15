package com.augustopreis.claripay.modules.charge.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.UpdateChargeStatusDTO;
import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.mapper.ChargeMapper;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateChargeStatusUseCase {

  private final ChargeRepository chargeRepository;
  private final ChargeMapper chargeMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ChargeDTO execute(Long id, UpdateChargeStatusDTO request) {
    Long userId = auth.getAuthenticatedId();

    Charge charge = chargeRepository
        .findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cobrança não encontrada"));

    charge.setStatus(request.getStatus());

    if (request.getStatus() == ChargeStatusEnum.PAID && charge.getPaidAt() == null) {
      charge.setPaidAt(LocalDateTime.now());
    }

    if (request.getStatus() != ChargeStatusEnum.PAID && charge.getPaidAt() != null) {
      charge.setPaidAt(null);
    }

    charge = chargeRepository.save(charge);

    return chargeMapper.toDTO(charge);
  }
}
