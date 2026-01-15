package com.augustopreis.claripay.config.seed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.augustopreis.claripay.modules.expense.enums.ExpenseTypeEnum;
import com.augustopreis.claripay.modules.expense.repository.ExpenseRepository;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;
import com.augustopreis.claripay.modules.user.repository.UserRepository;
import com.augustopreis.claripay.modules.user.repository.entity.User;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpenseSeeder {

  private final ExpenseRepository expenseRepository;
  private final UserRepository userRepository;

  private final Faker faker = new Faker(Locale.of("pt-BR"));
  private final Random random = new Random();

  public int seed() {
    long expenseCount = expenseRepository.count();

    if (expenseCount > 0) {
      return 0;
    }

    List<User> users = userRepository.findAll();

    if (users.isEmpty()) {
      return 0;
    }

    int expensesCreated = 0;

    for (User user : users) {
      int expensesToCreate = random.nextInt(30) + 10;

      for (int i = 0; i < expensesToCreate; i++) {
        ExpenseTypeEnum type = random.nextDouble() < 0.6
            ? ExpenseTypeEnum.BUSINESS
            : ExpenseTypeEnum.PERSONAL;

        LocalDate date = LocalDate.now().minusDays(random.nextInt(180));

        BigDecimal amount = BigDecimal.valueOf(
            faker.number().randomDouble(2, 10, type == ExpenseTypeEnum.BUSINESS ? 3000 : 1000));

        String description = generateDescription(type);

        Expense expense = Expense.builder()
            .description(description)
            .amount(amount)
            .date(date)
            .type(type)
            .user(user)
            .build();

        expenseRepository.save(expense);
        expensesCreated++;
      }
    }

    return expensesCreated;
  }

  private String generateDescription(ExpenseTypeEnum type) {
    if (type == ExpenseTypeEnum.BUSINESS) {
      String[] businessExpenses = {
          "Aluguel do escritório",
          "Conta de luz do estabelecimento",
          "Internet e telefone comercial",
          "Material de escritório",
          "Equipamento de informática",
          "Software e licenças",
          "Publicidade e marketing",
          "Fornecedor de matéria-prima",
          "Manutenção de equipamentos",
          "Serviços de contador",
          "Hospedagem de site",
          "Plataforma de pagamento",
          "Impostos e taxas",
          "Serviços de limpeza",
          "Seguro empresarial",
          "Treinamento e capacitação",
          "Combustível (entregas)",
          "Frete e logística",
          "Consultoria empresarial",
          "Ferramentas e instrumentos"
      };

      return businessExpenses[random.nextInt(businessExpenses.length)];
    } else {
      String[] personalExpenses = {
          "Retirada para alimentação",
          "Retirada para transporte pessoal",
          "Retirada para despesas médicas",
          "Retirada para educação",
          "Retirada para lazer",
          "Retirada para vestuário",
          "Retirada para moradia pessoal",
          "Retirada para contas pessoais",
          "Retirada para farmácia",
          "Retirada para academia",
          "Retirada para livros e cursos",
          "Retirada para manutenção veículo pessoal",
          "Retirada pró-labore",
          "Retirada para viagem pessoal",
          "Retirada para presente familiar"
      };

      return personalExpenses[random.nextInt(personalExpenses.length)];
    }
  }
}
