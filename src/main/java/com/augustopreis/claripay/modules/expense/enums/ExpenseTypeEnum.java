package com.augustopreis.claripay.modules.expense.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseTypeEnum {
  PERSONAL("Pessoal"),
  BUSINESS("Negócio");

  private final String description;
}
