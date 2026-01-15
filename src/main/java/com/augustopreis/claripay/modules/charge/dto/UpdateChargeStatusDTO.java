package com.augustopreis.claripay.modules.charge.dto;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateChargeStatusDTO {

  @NotNull(message = "O status é obrigatório")
  private ChargeStatusEnum status;
}
