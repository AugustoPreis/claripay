package com.augustopreis.claripay.config.seed;

import java.util.Locale;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.augustopreis.claripay.config.seed.util.SeederUtil;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSeeder {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  public int seed() {
    boolean adminCreated = this.seedAdminUser();

    long userCount = userRepository.count();
    int usersCreated = adminCreated ? 1 : 0;

    if (userCount > 1) {
      return usersCreated;
    }

    int usersToCreate = random.nextInt(20);

    for (int i = 1; i <= usersToCreate; i++) {
      String fullName = faker.name().fullName();

      User user = User.builder()
          .name(fullName)
          .email(SeederUtil.userNameToEmail(fullName))
          .password(passwordEncoder.encode("password"))
          .build();

      userRepository.save(user);
      usersCreated++;
    }

    return usersCreated;
  }

  private boolean seedAdminUser() {
    final boolean exists = userRepository.existsByEmail("admin@gmail.com");

    if (exists) {
      return false;
    }

    final User adminUser = User.builder()
        .name("Admin")
        .email("admin@gmail.com")
        .password(passwordEncoder.encode("admin123"))
        .build();

    userRepository.save(adminUser);

    return true;
  }
}
