package com.augustopreis.claripay.modules.auth.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.exception.BusinessException;
import com.augustopreis.claripay.modules.auth.dto.AuthResponseDTO;
import com.augustopreis.claripay.modules.auth.dto.RegisterRequestDTO;
import com.augustopreis.claripay.modules.user.mapper.UserMapper;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisterUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final UserMapper userMapper;

  @Transactional
  public AuthResponseDTO execute(RegisterRequestDTO request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new BusinessException("E-mail já cadastrado");
    }

    User user = userMapper.toEntity(request);

    user.setPassword(passwordEncoder.encode(request.getPassword()));

    user = userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail());

    return userMapper.toAuthResponse(user, token);
  }
}
