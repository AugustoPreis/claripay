package com.augustopreis.claripay.modules.alert.dto;

import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.alert.enums.AlertSeverityEnum;
import com.augustopreis.claripay.modules.alert.enums.AlertTypeEnum;

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
@Schema(description = "Dados do alerta")
public class AlertDTO {
  
  @Schema(description = "ID", example = "1")
  private Long id;
  
  @Schema(description = "Título", example = "Cobrança vencida")
  private String title;
  
  @Schema(description = "Mensagem", example = "A cobrança #123 venceu há 2 dias")
  private String message;
  
  @Schema(description = "Tipo do alerta")
  private AlertTypeEnum type;
  
  @Schema(description = "Severidade")
  private AlertSeverityEnum severity;
  
  @Schema(description = "Lido", example = "false")
  private Boolean read;
  
  @Schema(description = "Data de criação", example = "2026-01-16T10:30:00")
  private LocalDateTime createdAt;
}
