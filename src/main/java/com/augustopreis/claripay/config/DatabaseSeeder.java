package com.augustopreis.claripay.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  @Profile("!prod")
  public CommandLineRunner seedDatabase() {
    return args -> {
      log.info("Iniciando execução de seeds");

      this.seedAdminUser();
    };
  }

  private void seedAdminUser() {
    final boolean exists = userRepository.existsByEmail("admin@gmail.com");

    if (exists) {
      log.info("Usuário admin já existe. Ignorando seed.");
      return;
    }

    final User adminUser = User.builder()
        .name("Admin")
        .email("admin@gmail.com")
        .password(passwordEncoder.encode("admin123"))
        .build();

    userRepository.save(adminUser);

    log.info("Usuário admin criado com sucesso.");
  }
}
