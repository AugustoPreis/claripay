package com.augustopreis.claripay.modules.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.service.enums.RecurrenceTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados do serviço")
public class ServiceDTO {

  @Schema(description = "ID", example = "1")
  private Long id;

  @Schema(description = "Nome", example = "Consultoria Mensal")
  private String name;

  @Schema(description = "Descrição", example = "Serviço de consultoria")
  private String description;

  @Schema(description = "Valor padrão", example = "1500.00")
  private BigDecimal defaultValue;

  @Schema(description = "Tipo de recorrência")
  private RecurrenceTypeEnum recurrenceType;

  @Schema(description = "Intervalo de recorrência", example = "1")
  private Integer recurrenceInterval;

  @Schema(description = "Ativo", example = "true")
  private Boolean active;

  @Schema(description = "Data de criação", example = "2026-01-16T10:30:00")
  private LocalDateTime createdAt;

  @Schema(description = "Data de atualização", example = "2026-01-16T10:30:00")
  private LocalDateTime updatedAt;
}
