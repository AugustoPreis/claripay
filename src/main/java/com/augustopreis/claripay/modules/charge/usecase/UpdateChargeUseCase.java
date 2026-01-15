package com.augustopreis.claripay.modules.charge.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.UpdateChargeDTO;
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
public class UpdateChargeUseCase {

  private final ChargeRepository chargeRepository;
  private final CustomerRepository customerRepository;
  private final ServiceRepository serviceRepository;
  private final ChargeMapper chargeMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public ChargeDTO execute(Long id, UpdateChargeDTO request) {
    Long userId = auth.getAuthenticatedId();

    Charge charge = chargeRepository
        .findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cobrança não encontrada"));

    chargeMapper.updateChargeFromDTO(request, charge);

    if (request.getCustomerId() != null) {
      Customer customer = customerRepository
          .findByIdAndUserIdAndActiveTrue(request.getCustomerId(), userId)
          .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

      charge.setCustomer(customer);
    }

    if (request.getServiceId() != null) {
      Service service = serviceRepository
          .findByIdAndUserIdAndActiveTrue(request.getServiceId(), userId)
          .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));

      charge.setService(service);
    }

    charge = chargeRepository.save(charge);

    return chargeMapper.toDTO(charge);
  }
}
