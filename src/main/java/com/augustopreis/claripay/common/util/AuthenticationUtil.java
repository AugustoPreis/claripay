package com.augustopreis.claripay.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

  private final UserRepository userRepository;

  public User getAuthenticatedUser() {
    Long userId = getAuthenticatedId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    if (!user.getActive()) {
      throw new BusinessException("Usuário inativo");
    }

    return user;
  }

  public Long getAuthenticatedId() {
    Authentication authentication = getAuthentication();
    Object principal = authentication.getPrincipal();

    if (principal instanceof CustomUserDetails userDetails) {
      return userDetails.getId();
    }

    throw new BusinessException("Principal inválido");
  }

  public boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication != null && authentication.isAuthenticated();
  }

  private Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new BusinessException("Usuário não autenticado");
    }

    return authentication;
  }
}
