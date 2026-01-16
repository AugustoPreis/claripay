package com.augustopreis.claripay.modules.withdrawal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.withdrawal.enums.WithdrawalTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Retirada")
public class WithdrawalDTO {

  @Schema(description = "ID", example = "1")
  private Long id;
  
  @Schema(description = "Descrição", example = "Pró-labore")
  private String description;
  
  @Schema(description = "Valor", example = "5000.00")
  private BigDecimal amount;
  
  @Schema(description = "Data", example = "2024-01-31")
  private LocalDate date;
  
  @Schema(description = "Tipo")
  private WithdrawalTypeEnum type;
  
  @Schema(description = "Ativo", example = "true")
  private Boolean active;
  
  @Schema(description = "Data de criação", example = "2024-01-01T10:00:00")
  private LocalDateTime createdAt;
  
  @Schema(description = "Data de atualização", example = "2024-01-15T14:30:00")
  private LocalDateTime updatedAt;
}
