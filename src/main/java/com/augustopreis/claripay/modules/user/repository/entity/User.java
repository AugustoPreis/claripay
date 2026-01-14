package com.augustopreis.claripay.modules.user.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "uk_users_email", columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome é obrigatório")
  @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
  @Column(nullable = false, length = 100)
  private String name;

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "E-mail inválido")
  @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Column(nullable = false)
  private String password;

  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;

  @Builder.Default
  @Column(name = "email_verified", nullable = false)
  private Boolean emailVerified = false;

  @Column(name = "reset_token")
  private String resetToken;

  @Column(name = "reset_token_expiry")
  private LocalDateTime resetTokenExpiry;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
