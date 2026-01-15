package com.augustopreis.claripay.modules.service.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.mapper.ServiceMapper;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyServiceUseCase {

  private final ServiceRepository serviceRepository;
  private final ServiceMapper serviceMapper;
  private final AuthenticationUtil auth;

  public Page<ServiceDTO> execute(Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    Page<Service> services = serviceRepository.findAllByUserIdAndActiveTrue(userId, pageable);

    return services.map(serviceMapper::toDTO);
  }
}
