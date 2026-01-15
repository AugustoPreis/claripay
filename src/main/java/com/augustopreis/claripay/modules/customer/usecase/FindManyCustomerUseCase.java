package com.augustopreis.claripay.modules.customer.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.customer.dto.CustomerDTO;
import com.augustopreis.claripay.modules.customer.mapper.CustomerMapper;
import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindManyCustomerUseCase {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public Page<CustomerDTO> execute(Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    return customerRepository
        .findAllByUserIdAndActiveTrue(userId, pageable)
        .map(customerMapper::toDTO);
  }
}
