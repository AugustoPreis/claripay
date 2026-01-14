package com.augustopreis.claripay.modules.auth.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.mapper.UserMapper;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public UserDTO execute() {
    String email = getEmailFromAuthentication();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    if (!user.getActive()) {
      throw new BusinessException("Usuário inativo");
    }

    return userMapper.toDTO(user);
  }

  private String getEmailFromAuthentication() {
    Authentication authentication = getAuthentication();
    Object principal = authentication.getPrincipal();

    return (principal instanceof UserDetails userDetails)
        ? userDetails.getUsername()
        : null;
  }

  private Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new BusinessException("Usuário não autenticado");
    }

    return authentication;
  }
}
