package com.augustopreis.claripay.modules.auth.usecase;

import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.auth.dto.ForgotPasswordRequestDTO;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class ForgotPasswordUseCase {
  private static final int TOKEN_EXPIRY_HOURS = 24;

  private final UserRepository userRepository;

  public ForgotPasswordUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void execute(ForgotPasswordRequestDTO request) {
    log.info("Solicitação de recuperação de senha para: {}", request.getEmail());

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> {
          log.warn("Tentativa de recuperação de senha com e-mail não cadastrado: {}", request.getEmail());
          return new ResourceNotFoundException("Usuário não encontrado");
        });

    String resetToken = UUID.randomUUID().toString();
    user.setResetToken(resetToken);
    user.setResetTokenExpiry(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));

    userRepository.save(user);

    log.info("Token de recuperação gerado para: {}", request.getEmail());
  }
}
