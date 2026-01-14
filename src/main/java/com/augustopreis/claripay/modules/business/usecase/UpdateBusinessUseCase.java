package com.augustopreis.claripay.modules.business.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.dto.UpdateBusinessDTO;
import com.augustopreis.claripay.modules.business.mapper.BusinessMapper;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;
import com.augustopreis.claripay.modules.business.repository.entity.Business;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateBusinessUseCase {

  private final BusinessRepository businessRepository;
  private final BusinessMapper businessMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public BusinessDTO execute(Long id, UpdateBusinessDTO request) {
    Long userId = auth.getAuthenticatedId();

    Business business = businessRepository.findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Negócio não encontrado"));

    businessMapper.updateBusinessFromDTO(request, business);

    business = businessRepository.save(business);

    return businessMapper.toDTO(business);
  }
}
