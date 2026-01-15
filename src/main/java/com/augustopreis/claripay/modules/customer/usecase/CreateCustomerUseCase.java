package com.augustopreis.claripay.modules.customer.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.customer.dto.CreateCustomerDTO;
import com.augustopreis.claripay.modules.customer.dto.CustomerDTO;
import com.augustopreis.claripay.modules.customer.mapper.CustomerMapper;
import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateCustomerUseCase {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public CustomerDTO execute(CreateCustomerDTO request) {
    Customer customer = customerMapper.toEntity(request);

    customer.setUser(auth.getAuthenticatedUser());

    customer = customerRepository.save(customer);

    return customerMapper.toDTO(customer);
  }
}
