package com.augustopreis.claripay.modules.service.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.dto.UpdateServiceDTO;
import com.augustopreis.claripay.modules.service.mapper.ServiceMapper;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateServiceUseCase {

  private final ServiceRepository serviceRepository;
  private final ServiceMapper serviceMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ServiceDTO execute(Long id, UpdateServiceDTO request) {
    Long userId = auth.getAuthenticatedId();

    Service service = serviceRepository.findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

    serviceMapper.updateServiceFromDTO(request, service);

    service = serviceRepository.save(service);

    return serviceMapper.toDTO(service);
  }
}
