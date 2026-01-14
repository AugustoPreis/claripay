package com.augustopreis.claripay.modules.user.service;

import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> {
          log.warn("Usuário não encontrado: {}", email);
          return new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
        });

    if (!user.getActive()) {
      log.warn("Tentativa de login com usuário inativo: {}", email);
      throw new UsernameNotFoundException("Usuário inativo");
    }

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!user.getActive())
        .build();
  }
}
