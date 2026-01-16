package com.augustopreis.claripay.modules.service.dto;

import java.math.BigDecimal;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Dados para criar serviço")
public class CreateServiceDTO {

  @Schema(description = "Nome", example = "Consultoria Mensal")
  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Schema(description = "Descrição", example = "Serviço de consultoria")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Schema(description = "Valor padrão", example = "1500.00")
  @NotNull(message = "O valor padrão é obrigatório")
  @DecimalMin(value = "0.01", message = "O valor padrão deve ser maior que zero")
  private BigDecimal defaultValue;

  @Schema(description = "Tipo de recorrência")
  private RecurrenceTypeEnum recurrenceType;

  @Schema(description = "Intervalo de recorrência", example = "1")
  @Min(value = 1, message = "O intervalo de recorrência deve ser maior que zero")
  private Integer recurrenceInterval;
}
