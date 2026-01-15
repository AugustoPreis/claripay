package com.augustopreis.claripay.modules.service.enums;

import lombok.Getter;

@Getter
public enum RecurrenceTypeEnum {
  DAILY("Diário"),
  WEEKLY("Semanal"),
  MONTHLY("Mensal"),
  YEARLY("Anual"),
  CUSTOM("Personalizado");

  private final String description;

  RecurrenceTypeEnum(String description) {
    this.description = description;
  }
}
