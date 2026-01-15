package com.augustopreis.claripay.modules.service.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome é obrigatório")
  @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
  @Column(nullable = false, length = 100)
  private String name;

  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  @Column(length = 500)
  private String description;

  @NotNull(message = "O valor padrão é obrigatório")
  @DecimalMin(value = "0.0", inclusive = false, message = "O valor padrão deve ser maior que zero")
  @Column(name = "default_value", nullable = false, precision = 19, scale = 2)
  private BigDecimal defaultValue;

  @Enumerated(EnumType.STRING)
  @Column(name = "recurrence_type", length = 50)
  private RecurrenceTypeEnum recurrenceType;

  @Min(value = 1, message = "O intervalo de recorrência deve ser maior que zero")
  @Column(name = "recurrence_interval")
  private Integer recurrenceInterval;

  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
