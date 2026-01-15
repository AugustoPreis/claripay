package com.augustopreis.claripay.modules.customer.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.customer.dto.CustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.UpdateCustomerDTO;
import com.augustopreis.claripay.modules.customer.mapper.CustomerMapper;
import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public CustomerDTO execute(Long id, UpdateCustomerDTO request) {
    Long userId = auth.getAuthenticatedId();

    Customer customer = customerRepository
        .findByIdAndUserIdAndActiveTrue(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

    customerMapper.updateCustomerFromDTO(request, customer);

    customer = customerRepository.save(customer);

    return customerMapper.toDTO(customer);
  }
}
