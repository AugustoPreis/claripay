package com.augustopreis.claripay.modules.service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.service.repository.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

  Optional<Service> findByIdAndUserId(Long id, Long userId);

  Optional<Service> findByIdAndUserIdAndActiveTrue(Long id, Long userId);

  Page<Service> findAllByUserId(Long userId, Pageable pageable);

  Page<Service> findAllByUserIdAndActiveTrue(Long userId, Pageable pageable);
}
