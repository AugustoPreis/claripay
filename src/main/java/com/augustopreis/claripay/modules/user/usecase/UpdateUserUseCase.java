package com.augustopreis.claripay.modules.user.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.user.dto.UpdateUserRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.mapper.UserMapper;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateUserUseCase {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public UserDTO execute(UpdateUserRequestDTO request) {
    String email = getEmailFromAuthentication();
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

    if (!user.getActive()) {
      throw new BusinessException("Usuário inativo");
    }

    validateEmailIsUnique(request.getEmail(), user);

    userMapper.updateUserFromDTO(request, user);
    User updatedUser = userRepository.save(user);

    return userMapper.toDTO(updatedUser);
  }

  private void validateEmailIsUnique(String email, User currentUser) {
    final boolean emailChanged = email != null && !email.equals(currentUser.getEmail());

    if (!emailChanged) {
      return;
    }

    final boolean emailExists = userRepository.existsByEmail(email);

    if (emailExists) {
      throw new BusinessException("E-mail já cadastrado");
    }
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
