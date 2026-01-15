package com.augustopreis.claripay.modules.charge.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.CreateChargeDTO;
import com.augustopreis.claripay.modules.charge.mapper.ChargeMapper;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateChargeUseCase {

  private final ChargeRepository chargeRepository;
  private final CustomerRepository customerRepository;
  private final ServiceRepository serviceRepository;
  private final ChargeMapper chargeMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ChargeDTO execute(CreateChargeDTO request) {
    Long userId = auth.getAuthenticatedId();

    Charge charge = chargeMapper.toEntity(request);

    Customer customer = customerRepository
        .findByIdAndUserIdAndActiveTrue(request.getCustomerId(), userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

    charge.setCustomer(customer);

    if (request.getServiceId() != null) {
      Service service = serviceRepository
          .findByIdAndUserIdAndActiveTrue(request.getServiceId(), userId)
          .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

      charge.setService(service);
    }

    charge.setUser(auth.getAuthenticatedUser());

    charge = chargeRepository.save(charge);

    return chargeMapper.toDTO(charge);
  }
}
