package com.augustopreis.claripay.modules.business.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.augustopreis.claripay.modules.business.repository.entity.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

  Optional<Business> findByIdAndUserId(Long id, Long userId);

  Page<Business> findAllByUserId(Long userId, Pageable pageable);
}
