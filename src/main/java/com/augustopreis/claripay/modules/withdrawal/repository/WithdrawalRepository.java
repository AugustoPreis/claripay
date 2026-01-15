package com.augustopreis.claripay.modules.withdrawal.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

  Page<Withdrawal> findByUserIdAndActiveTrue(Long userId, Pageable pageable);

  Optional<Withdrawal> findByIdAndUserIdAndActiveTrue(Long id, Long userId);

  @Query("SELECT COALESCE(SUM(w.amount), 0) FROM Withdrawal w " +
      "WHERE w.user.id = :userId " +
      "AND w.date BETWEEN :startDate AND :endDate " +
      "AND w.active = true")
  BigDecimal sumByUserIdAndDateBetween(
      @Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}
