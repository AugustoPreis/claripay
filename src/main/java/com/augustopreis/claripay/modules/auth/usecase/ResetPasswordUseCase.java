package com.augustopreis.claripay.modules.auth.usecase;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.auth.dto.ResetPasswordRequestDTO;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResetPasswordUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void execute(ResetPasswordRequestDTO request) {
    log.info("Tentativa de reset de senha com token: {}", request.getToken());

    User user = userRepository.findByResetToken(request.getToken())
        .orElseThrow(() -> {
          log.warn("Token de reset inválido: {}", request.getToken());
          return new BusinessException("Token inválido ou expirado");
        });

    if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
      log.warn("Token de reset expirado para usuário: {}", user.getEmail());
      throw new BusinessException("Token inválido ou expirado");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    user.setResetToken(null);
    user.setResetTokenExpiry(null);

    userRepository.save(user);
  }
}
