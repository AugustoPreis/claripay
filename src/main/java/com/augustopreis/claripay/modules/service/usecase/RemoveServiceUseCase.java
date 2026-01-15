package com.augustopreis.claripay.modules.service.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveServiceUseCase {

  private final ServiceRepository serviceRepository;
  private final AuthenticationUtil auth;

  @Transactional
  public void execute(Long id) {
    Long userId = auth.getAuthenticatedId();

    Service service = serviceRepository.findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

    service.setActive(false);

    serviceRepository.save(service);
  }
}
