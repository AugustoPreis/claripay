package com.augustopreis.claripay.config.seed;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerSeeder {

  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  public int seed() {
    long customerCount = customerRepository.count();

    if (customerCount > 1) {
      return 0;
    }

    List<User> users = userRepository.findAll();
    int customersCreated = 0;

    for (User user : users) {
      int customersToCreate = random.nextInt(20);

      for (int i = 1; i <= customersToCreate; i++) {

        Customer customer = Customer.builder()
            .name(faker.name().fullName())
            .document(faker.number().digits(11))
            .email(faker.internet().emailAddress())
            .cellphone(faker.phoneNumber().cellPhone())
            .user(user)
            .build();

        customerRepository.save(customer);
        customersCreated++;
      }
    }

    return customersCreated;
  }
}
