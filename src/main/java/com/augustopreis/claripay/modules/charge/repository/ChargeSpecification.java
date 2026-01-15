package com.augustopreis.claripay.modules.charge.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

public class ChargeSpecification {

  public static Specification<Charge> hasUserId(Long userId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
  }

  public static Specification<Charge> isActive() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("active"));
  }

  public static Specification<Charge> hasCustomerId(Long customerId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer").get("id"), customerId);
  }

  public static Specification<Charge> hasMinAmount(BigDecimal minAmount) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
  }

  public static Specification<Charge> hasMaxAmount(BigDecimal maxAmount) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
  }

  public static Specification<Charge> hasStatus(ChargeStatusEnum status) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
  }
}
