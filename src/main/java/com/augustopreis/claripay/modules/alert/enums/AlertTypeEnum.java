package com.augustopreis.claripay.modules.alert.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlertTypeEnum {
  LATE_CHARGE("Cobrança Atrasada"),
  UPCOMING_CHARGE("Cobrança Próxima"),
  LOW_BALANCE("Saldo Baixo"),
  NEGATIVE_BALANCE("Saldo Negativo");

  private final String description;
}
