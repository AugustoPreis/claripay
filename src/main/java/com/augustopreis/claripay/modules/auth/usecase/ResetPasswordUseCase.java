package com.augustopreis.claripay.modules.auth.usecase;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.auth.dto.ResetPasswordRequestDTO;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void execute(ResetPasswordRequestDTO request) {

    User user = userRepository.findByResetToken(request.getToken())
        .orElseThrow(() -> this.invalidTokenException());

    if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
      throw this.invalidTokenException();
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    user.setResetToken(null);
    user.setResetTokenExpiry(null);

    userRepository.save(user);
  }

  private BusinessException invalidTokenException() {
    return new BusinessException("Token inválido ou expirado");
  }
}
