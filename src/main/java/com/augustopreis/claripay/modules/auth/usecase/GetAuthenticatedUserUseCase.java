package com.augustopreis.claripay.modules.auth.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.mapper.UserMapper;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {

  private final AuthenticationUtil authenticationUtil;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public UserDTO execute() {
    User user = authenticationUtil.getAuthenticatedUser();

    return userMapper.toDTO(user);
  }
}
