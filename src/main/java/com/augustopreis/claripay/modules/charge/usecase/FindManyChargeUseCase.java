package com.augustopreis.claripay.modules.charge.usecase;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.mapper.ChargeMapper;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.ChargeSpecification;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyChargeUseCase {

  private final ChargeRepository chargeRepository;
  private final ChargeMapper chargeMapper;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public Page<ChargeDTO> execute(
      Long customerId,
      BigDecimal minAmount,
      BigDecimal maxAmount,
      ChargeStatusEnum status,
      Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    Specification<Charge> spec = Specification.where(ChargeSpecification.hasUserId(userId))
        .and(ChargeSpecification.isActive());

    if (customerId != null) {
      spec = spec.and(ChargeSpecification.hasCustomerId(customerId));
    }

    if (minAmount != null) {
      spec = spec.and(ChargeSpecification.hasMinAmount(minAmount));
    }

    if (maxAmount != null) {
      spec = spec.and(ChargeSpecification.hasMaxAmount(maxAmount));
    }

    if (status != null) {
      spec = spec.and(ChargeSpecification.hasStatus(status));
    }

    return chargeRepository
        .findAll(spec, pageable)
        .map(chargeMapper::toDTO);
  }
}
