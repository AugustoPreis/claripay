package com.augustopreis.claripay.modules.auth.usecase;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.auth.dto.AuthResponseDTO;
import com.augustopreis.claripay.modules.auth.dto.LoginRequestDTO;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.user.mapper.UserMapper;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public AuthResponseDTO execute(LoginRequestDTO request) {
    log.info("Tentativa de login para o e-mail: {}", request.getEmail());

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> {
          log.warn("Tentativa de login com e-mail não cadastrado: {}", request.getEmail());
          return this.invalidLoginException();
        });

    if (!user.getActive()) {
      log.warn("Tentativa de login com usuário inativo: {}", request.getEmail());
      throw new BusinessException("Usuário inativo");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      log.warn("Tentativa de login com senha incorreta para: {}", request.getEmail());
      throw this.invalidLoginException();
    }

    String token = jwtUtil.generateToken(user.getEmail());

    return userMapper.toAuthResponse(user, token);
  }

  private BusinessException invalidLoginException() {
    return new BusinessException("E-mail ou senha inválidos");
  }
}
