package com.augustopreis.claripay.modules.charge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long>, JpaSpecificationExecutor<Charge> {

  Optional<Charge> findByIdAndUserId(Long id, Long userId);

  Optional<Charge> findByIdAndUserIdAndActiveTrue(Long id, Long userId);
}
