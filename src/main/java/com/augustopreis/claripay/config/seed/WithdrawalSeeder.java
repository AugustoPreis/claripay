package com.augustopreis.claripay.config.seed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;
import com.augustopreis.claripay.modules.withdrawal.repository.WithdrawalRepository;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WithdrawalSeeder {

  private final WithdrawalRepository withdrawalRepository;
  private final UserRepository userRepository;

  private final Random random = new Random();

  public int seed() {
    long count = withdrawalRepository.count();

    if (count > 0) {
      return 0;
    }

    List<User> users = userRepository.findAll();

    if (users.isEmpty()) {
      return 0;
    }

    int withdrawalsCreated = 0;

    for (User user : users) {
      List<Withdrawal> withdrawals = new ArrayList<>();

      LocalDate startDate = LocalDate.now().minusMonths(6);
      LocalDate endDate = LocalDate.now();
      LocalDate currentDate = startDate;

      while (!currentDate.isAfter(endDate)) {
        int withdrawalsInMonth = 2 + random.nextInt(2);

        for (int i = 0; i < withdrawalsInMonth; i++) {
          int dayOfMonth = 1 + random.nextInt(28);
          LocalDate withdrawalDate = currentDate.withDayOfMonth(Math.min(dayOfMonth, currentDate.lengthOfMonth()));

          if (withdrawalDate.isAfter(LocalDate.now())) {
            continue;
          }

          WithdrawalTypeEnum type = getRandomType(random);
          BigDecimal amount = generateAmount(type, random);
          String description = generateDescription(type, withdrawalDate);

          Withdrawal withdrawal = Withdrawal.builder()
              .description(description)
              .amount(amount)
              .date(withdrawalDate)
              .type(type)
              .user(user)
              .build();

          withdrawals.add(withdrawal);
        }

        currentDate = currentDate.plusMonths(1);
      }

      withdrawalRepository.saveAll(withdrawals);
      withdrawalsCreated += withdrawals.size();
    }

    return withdrawalsCreated;
  }

  private WithdrawalTypeEnum getRandomType(Random random) {
    WithdrawalTypeEnum[] types = WithdrawalTypeEnum.values();
    int index = random.nextInt(types.length);

    if (random.nextInt(100) < 70) {
      return WithdrawalTypeEnum.PRO_LABORE;
    }

    return types[index];
  }

  private BigDecimal generateAmount(WithdrawalTypeEnum type, Random random) {
    return switch (type) {
      case PRO_LABORE -> BigDecimal.valueOf(2000 + random.nextInt(3001));
      case DIVIDEND -> BigDecimal.valueOf(1000 + random.nextInt(2001));
      case PERSONAL_USE -> BigDecimal.valueOf(500 + random.nextInt(1501));
      case REINVESTMENT -> BigDecimal.valueOf(1000 + random.nextInt(3001));
      default -> BigDecimal.valueOf(1000);
    };
  }

  private String generateDescription(WithdrawalTypeEnum type, LocalDate date) {
    String month = date.getMonth().toString().toLowerCase();
    String year = String.valueOf(date.getYear());

    return switch (type) {
      case PRO_LABORE -> "Pró-labore referente a " + month + "/" + year;
      case DIVIDEND -> "Distribuição de lucros - " + month + "/" + year;
      case PERSONAL_USE -> "Retirada para uso pessoal";
      case REINVESTMENT -> "Reinvestimento em educação/equipamentos";
      default -> "Retirada do negócio";
    };
  }
}
