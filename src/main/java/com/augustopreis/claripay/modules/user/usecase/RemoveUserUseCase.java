package com.augustopreis.claripay.modules.user.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveUserUseCase {

  private final AuthenticationUtil authenticationUtil;
  private final UserRepository userRepository;

  @Transactional
  public void execute() {
    User user = authenticationUtil.getAuthenticatedUser();

    user.setActive(false);

    userRepository.save(user);
  }
}
