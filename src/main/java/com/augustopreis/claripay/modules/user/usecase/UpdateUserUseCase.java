package com.augustopreis.claripay.modules.user.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
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

  private final AuthenticationUtil authenticationUtil;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public UserDTO execute(UpdateUserRequestDTO request) {
    User user = authenticationUtil.getAuthenticatedUser();

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
}
