package com.augustopreis.claripay.config.seed;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServiceSeeder {

  private final ServiceRepository serviceRepository;
  private final UserRepository userRepository;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  private static final String[] SERVICE_TYPES = {
      "Consultoria", "Assessoria", "Desenvolvimento", "Design", "Marketing",
      "Treinamento", "Suporte Técnico", "Manutenção", "Instalação", "Auditoria"
  };

  public int seed() {
    long serviceCount = serviceRepository.count();

    if (serviceCount > 0) {
      return 0;
    }

    List<User> users = userRepository.findAll();
    int servicesCreated = 0;

    RecurrenceTypeEnum[] recurrenceTypes = RecurrenceTypeEnum.values();

    for (User user : users) {
      int servicesToCreate = random.nextInt(20);

      for (int i = 1; i <= servicesToCreate; i++) {
        RecurrenceTypeEnum randomRecurrence = random.nextBoolean()
            ? recurrenceTypes[random.nextInt(recurrenceTypes.length)]
            : null;

        Integer recurrenceInterval = randomRecurrence == RecurrenceTypeEnum.CUSTOM
            ? random.nextInt(30) + 1
            : null;

        String serviceType = SERVICE_TYPES[random.nextInt(SERVICE_TYPES.length)];

        Service service = Service.builder()
            .name(serviceType + " de " + faker.job().field())
            .description(faker.lorem().sentence(10))
            .defaultValue(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 10000)))
            .recurrenceType(randomRecurrence)
            .recurrenceInterval(recurrenceInterval)
            .user(user)
            .build();

        serviceRepository.save(service);
        servicesCreated++;
      }
    }

    return servicesCreated;
  }
}
