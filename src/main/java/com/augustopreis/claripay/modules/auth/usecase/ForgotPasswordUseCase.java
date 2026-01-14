package com.augustopreis.claripay.modules.auth.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.auth.dto.ForgotPasswordRequestDTO;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

@Component
public class ForgotPasswordUseCase {
  private static final int TOKEN_EXPIRY_HOURS = 24;

  private final UserRepository userRepository;

  public ForgotPasswordUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void execute(ForgotPasswordRequestDTO request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

    String resetToken = UUID.randomUUID().toString();
    user.setResetToken(resetToken);
    user.setResetTokenExpiry(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));

    userRepository.save(user);
  }
}
