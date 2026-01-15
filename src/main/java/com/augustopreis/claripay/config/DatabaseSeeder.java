package com.augustopreis.claripay.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.augustopreis.claripay.config.seed.BusinessSeeder;
import com.augustopreis.claripay.config.seed.ServiceSeeder;
import com.augustopreis.claripay.config.seed.UserSeeder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

  private final UserSeeder userSeeder;
  private final BusinessSeeder businessSeeder;
  private final ServiceSeeder serviceSeeder;

  @Bean
  @Profile("!prod")
  public CommandLineRunner seedDatabase() {
    return args -> {
      userSeeder.seed();
      businessSeeder.seed();
      serviceSeeder.seed();
    };
  }
}