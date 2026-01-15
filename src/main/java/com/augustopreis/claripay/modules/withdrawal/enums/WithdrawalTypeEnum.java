package com.augustopreis.claripay.modules.withdrawal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WithdrawalTypeEnum {
  PRO_LABORE("Pró-labore"),
  DIVIDEND("Distribuição de Lucros"),
  PERSONAL_USE("Uso Pessoal"),
  REINVESTMENT("Reinvestimento Pessoal");

  private final String description;
}
