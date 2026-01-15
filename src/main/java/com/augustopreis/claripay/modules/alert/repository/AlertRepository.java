package com.augustopreis.claripay.modules.alert.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.alert.repository.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

  Optional<Alert> findByIdAndUserId(Long id, Long userId);

  Page<Alert> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

  Page<Alert> findByUserIdAndReadOrderByCreatedAtDesc(Long userId, Boolean read, Pageable pageable);

  Long countByUserIdAndRead(Long userId, Boolean read);
}
