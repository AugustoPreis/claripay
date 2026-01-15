package com.augustopreis.claripay.modules.charge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChargeStatusEnum {
  PENDING("Pendente"),
  PAID("Pago"),
  LATE("Atrasado"),
  CANCELED("Cancelado");

  private final String description;
}