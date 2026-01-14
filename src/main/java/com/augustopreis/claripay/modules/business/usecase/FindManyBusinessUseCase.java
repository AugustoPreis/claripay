package com.augustopreis.claripay.modules.business.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.mapper.BusinessMapper;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;
import com.augustopreis.claripay.modules.business.repository.entity.Business;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyBusinessUseCase {

  private final BusinessRepository businessRepository;
  private final BusinessMapper businessMapper;
  private final AuthenticationUtil auth;

  public Page<BusinessDTO> execute(Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    Page<Business> businesses = businessRepository.findAllByUserId(userId, pageable);

    return businesses.map(businessMapper::toDTO);
  }
}
