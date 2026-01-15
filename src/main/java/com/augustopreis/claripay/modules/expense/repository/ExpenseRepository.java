package com.augustopreis.claripay.modules.expense.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  Optional<Expense> findByIdAndUserId(Long id, Long userId);

  Page<Expense> findAllByUserId(Long userId, Pageable pageable);

  Page<Expense> findAllByUserIdAndType(Long userId, ExpenseTypeEnum type, Pageable pageable);

  @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
  Page<Expense> findAllByUserIdAndDateBetween(
      @Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      Pageable pageable);

  @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
      "WHERE e.user.id = :userId " +
      "AND e.type = :type " +
      "AND e.date BETWEEN :startDate AND :endDate")
  BigDecimal sumByUserIdAndTypeAndDateBetween(
      @Param("userId") Long userId,
      @Param("type") ExpenseTypeEnum type,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}
