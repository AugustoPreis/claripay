package com.augustopreis.claripay.modules.auth.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.auth.dto.ForgotPasswordRequestDTO;
import com.augustopreis.claripay.modules.email.service.EmailService;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordUseCase {
  private static final int TOKEN_EXPIRY_HOURS = 24;

  private final UserRepository userRepository;
  private final EmailService emailService;

  @Transactional
  public void execute(ForgotPasswordRequestDTO request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

    String resetToken = UUID.randomUUID().toString();
    user.setResetToken(resetToken);
    user.setResetTokenExpiry(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));

    userRepository.save(user);

    sendEmail(user);
  }

  private void sendEmail(User user) {
    emailService.sendForgotPasswordEmail(user.getEmail(), user.getName(), user.getResetToken());
  }
}
