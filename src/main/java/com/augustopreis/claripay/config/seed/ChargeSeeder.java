package com.augustopreis.claripay.config.seed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;
import com.augustopreis.claripay.modules.charge.repository.ChargeRepository;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;
import com.augustopreis.claripay.modules.customer.repository.CustomerRepository;
import com.augustopreis.claripay.modules.customer.repository.entity.Customer;
import com.augustopreis.claripay.modules.service.repository.ServiceRepository;
import com.augustopreis.claripay.modules.service.repository.entity.Service;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChargeSeeder {

  private final ChargeRepository chargeRepository;
  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final ServiceRepository serviceRepository;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  public int seed() {
    long chargeCount = chargeRepository.count();

    if (chargeCount > 0) {
      return 0;
    }

    List<User> users = userRepository.findAll();
    List<Customer> allCustomers = customerRepository.findAll();
    List<Service> allServices = serviceRepository.findAll();

    if (users.isEmpty() || allCustomers.isEmpty()) {
      return 0;
    }

    int chargesCreated = 0;

    for (User user : users) {
      List<Customer> userCustomers = allCustomers.stream()
          .filter(c -> c.getUser().getId().equals(user.getId()))
          .toList();

      List<Service> userServices = allServices.stream()
          .filter(s -> s.getUser().getId().equals(user.getId()))
          .toList();

      if (userCustomers.isEmpty()) {
        continue;
      }

      for (Customer customer : userCustomers) {
        int chargesToCreate = random.nextInt(11);

        for (int i = 0; i < chargesToCreate; i++) {
          Service service = (!userServices.isEmpty() && random.nextDouble() < 0.7)
              ? userServices.get(random.nextInt(userServices.size()))
              : null;

          LocalDate dueDate = LocalDate.now().plusDays(random.nextInt(91) - 30);

          ChargeStatusEnum status;
          LocalDateTime paidAt = null;

          if (dueDate.isBefore(LocalDate.now())) {
            double statusRandom = random.nextDouble();
            if (statusRandom < 0.6) {
              status = ChargeStatusEnum.PAID;
              paidAt = dueDate.atTime(random.nextInt(24), random.nextInt(60));
            } else if (statusRandom < 0.9) {
              status = ChargeStatusEnum.LATE;
            } else {
              status = ChargeStatusEnum.CANCELED;
            }
          } else {
            double statusRandom = random.nextDouble();
            if (statusRandom < 0.8) {
              status = ChargeStatusEnum.PENDING;
            } else if (statusRandom < 0.95) {
              status = ChargeStatusEnum.PAID;
              paidAt = LocalDateTime.now().minusDays(random.nextInt(5));
            } else {
              status = ChargeStatusEnum.CANCELED;
            }
          }

          BigDecimal amount = service != null
              ? service.getDefaultValue()
              : BigDecimal.valueOf(faker.number().randomDouble(2, 10, 5000));

          String pixCode = (status == ChargeStatusEnum.PENDING || status == ChargeStatusEnum.LATE)
              && random.nextDouble() < 0.8
                  ? generatePixCode()
                  : null;

          String paymentLink = (status == ChargeStatusEnum.PENDING || status == ChargeStatusEnum.LATE)
              && random.nextDouble() < 0.6
                  ? "https://claripay.com/pay/" + faker.internet().uuid()
                  : null;

          Charge charge = Charge.builder()
              .amount(amount)
              .description(generateDescription(service))
              .dueDate(dueDate)
              .status(status)
              .paidAt(paidAt)
              .pixCode(pixCode)
              .paymentLink(paymentLink)
              .active(random.nextDouble() < 0.95)
              .customer(customer)
              .service(service)
              .user(user)
              .build();

          chargeRepository.save(charge);
          chargesCreated++;
        }
      }
    }

    return chargesCreated;
  }

  private String generateDescription(Service service) {
    if (service != null && random.nextDouble() < 0.7) {
      return service.getName();
    }

    String[] descriptionTemplates = {
        "Pagamento de %s",
        "Cobrança de %s",
        "Fatura de %s",
        "Mensalidade de %s",
        "Prestação de %s"
    };

    String template = descriptionTemplates[random.nextInt(descriptionTemplates.length)];
    return String.format(template, faker.commerce().productName());
  }

  private String generatePixCode() {
    return "00020126" + faker.number().digits(50) + "6304" + faker.number().digits(4);
  }
}
