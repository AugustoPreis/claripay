package com.augustopreis.claripay.modules.business.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.dto.CreateBusinessDTO;
import com.augustopreis.claripay.modules.business.mapper.BusinessMapper;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;
import com.augustopreis.claripay.modules.business.repository.entity.Business;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateBusinessUseCase {

  private final BusinessRepository businessRepository;
  private final BusinessMapper businessMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public BusinessDTO execute(CreateBusinessDTO request) {
    Business business = businessMapper.toEntity(request);

    business.setUser(auth.getAuthenticatedUser());

    business = businessRepository.save(business);

    return businessMapper.toDTO(business);
  }
}
