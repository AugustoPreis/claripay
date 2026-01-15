package com.augustopreis.claripay.modules.charge.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;
import com.augustopreis.claripay.modules.service.repository.entity.Service;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "charges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Charge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "O valor é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  @Column(length = 500)
  private String description;

  @NotNull(message = "A data de vencimento é obrigatória")
  @Column(name = "due_date", nullable = false)
  private LocalDate dueDate;

  @Builder.Default
  @NotNull(message = "O status é obrigatório")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ChargeStatusEnum status = ChargeStatusEnum.PENDING;

  @Column(name = "paid_at")
  private LocalDateTime paidAt;

  @Column(name = "pix_code", length = 500)
  private String pixCode;

  @Column(name = "payment_link", length = 500)
  private String paymentLink;

  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @NotNull(message = "O cliente é obrigatório")
  @ManyToOne(optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "service_id")
  private Service service;

  @NotNull(message = "O usuário é obrigatório")
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
