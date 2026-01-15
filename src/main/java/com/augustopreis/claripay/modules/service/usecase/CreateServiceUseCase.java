package com.augustopreis.claripay.modules.service.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.service.dto.CreateServiceDTO;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.mapper.ServiceMapper;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateServiceUseCase {

  private final ServiceRepository serviceRepository;
  private final ServiceMapper serviceMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ServiceDTO execute(CreateServiceDTO request) {
    Service service = serviceMapper.toEntity(request);

    service.setUser(auth.getAuthenticatedUser());

    service = serviceRepository.save(service);

    return serviceMapper.toDTO(service);
  }
}
