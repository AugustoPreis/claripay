package com.augustopreis.claripay.modules.customer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.customer.repository.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByIdAndUserId(Long id, Long userId);

  Optional<Customer> findByIdAndUserIdAndActiveTrue(Long id, Long userId);

  Page<Customer> findAllByUserId(Long userId, Pageable pageable);

  Page<Customer> findAllByUserIdAndActiveTrue(Long userId, Pageable pageable);
}
