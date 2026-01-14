package com.augustopreis.claripay.modules.business.usecase;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.mapper.BusinessMapper;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindOneBusinessUseCase {

  private final BusinessRepository businessRepository;
  private final BusinessMapper businessMapper;
  private final AuthenticationUtil auth;

  public BusinessDTO execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    var business = businessRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Negócio não encontrado"));

    return businessMapper.toDTO(business);
  }
}
