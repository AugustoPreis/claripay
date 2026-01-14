package com.augustopreis.claripay.modules.business.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;
import com.augustopreis.claripay.modules.business.repository.entity.Business;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveBusinessUseCase {

  private final BusinessRepository businessRepository;
  private final AuthenticationUtil auth;

  @Transactional
  public void execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Business business = businessRepository.findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Negócio não encontrado"));

    business.setActive(false);

    businessRepository.save(business);
  }
}
