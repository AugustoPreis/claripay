package com.augustopreis.claripay.modules.alert.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlertSeverityEnum {
  INFO("Informação"),
  WARNING("Aviso"),
  CRITICAL("Crítico");

  private final String description;
}
