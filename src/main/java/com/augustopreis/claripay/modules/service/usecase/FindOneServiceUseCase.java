package com.augustopreis.claripay.modules.service.usecase;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.mapper.ServiceMapper;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindOneServiceUseCase {

  private final ServiceRepository serviceRepository;
  private final ServiceMapper serviceMapper;
  private final AuthenticationUtil auth;

  public ServiceDTO execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    var service = serviceRepository.findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

    return serviceMapper.toDTO(service);
  }
}
