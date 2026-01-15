package com.augustopreis.claripay.modules.alert.repository.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.augustopreis.claripay.modules.alert.enums.AlertSeverityEnum;
import com.augustopreis.claripay.modules.alert.enums.AlertTypeEnum;
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
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Alert {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O título é obrigatório")
  @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
  @Column(nullable = false, length = 200)
  private String title;

  @NotBlank(message = "A mensagem é obrigatória")
  @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres")
  @Column(nullable = false, length = 1000)
  private String message;

  @NotNull(message = "O tipo do alerta é obrigatório")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private AlertTypeEnum type;

  @NotNull(message = "A severidade do alerta é obrigatória")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AlertSeverityEnum severity;

  @Builder.Default
  @Column(nullable = false)
  private Boolean read = false;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
