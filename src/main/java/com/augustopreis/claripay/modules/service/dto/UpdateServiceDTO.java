package com.augustopreis.claripay.modules.service.dto;

import java.math.BigDecimal;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class UpdateServiceDTO {

  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @DecimalMin(value = "0.01", message = "O valor padrão deve ser maior que zero")
  private BigDecimal defaultValue;

  private RecurrenceTypeEnum recurrenceType;

  @Min(value = 1, message = "O intervalo de recorrência deve ser maior que zero")
  private Integer recurrenceInterval;

  private Boolean active;
}
