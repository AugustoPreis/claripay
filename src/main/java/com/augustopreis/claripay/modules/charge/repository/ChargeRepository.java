package com.augustopreis.claripay.modules.charge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long>, JpaSpecificationExecutor<Charge> {

  Optional<Charge> findByIdAndUserId(Long id, Long userId);

  Optional<Charge> findByIdAndUserIdAndActiveTrue(Long id, Long userId);

  List<Charge> findByDueDateBeforeAndStatusAndActiveTrue(LocalDate date, ChargeStatusEnum status);
}
