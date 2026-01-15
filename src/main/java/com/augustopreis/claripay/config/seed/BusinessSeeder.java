package com.augustopreis.claripay.config.seed;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.business.enums.BusinessTypeEnum;
import com.augustopreis.claripay.modules.business.repository.BusinessRepository;
import com.augustopreis.claripay.modules.business.repository.entity.Business;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BusinessSeeder {

  private final BusinessRepository businessRepository;
  private final UserRepository userRepository;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  public int seed() {
    long businessCount = businessRepository.count();

    if (businessCount > 0) {
      return 0;
    }

    List<User> users = userRepository.findAll();
    int businessesCreated = 0;

    BusinessTypeEnum[] businessTypes = BusinessTypeEnum.values();

    for (User user : users) {
      int businessesToCreate = random.nextInt(20);

      for (int i = 1; i <= businessesToCreate; i++) {
        BusinessTypeEnum randomType = businessTypes[random.nextInt(businessTypes.length)];

        Business business = Business.builder()
            .name(faker.company().name())
            .description(faker.company().catchPhrase())
            .document(faker.number().digits(14))
            .email(faker.internet().emailAddress())
            .cellphone(faker.phoneNumber().cellPhone())
            .type(randomType)
            .user(user)
            .build();

        businessRepository.save(business);
        businessesCreated++;
      }
    }

    return businessesCreated;
  }
}
