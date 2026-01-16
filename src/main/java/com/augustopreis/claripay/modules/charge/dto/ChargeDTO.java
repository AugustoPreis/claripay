package com.augustopreis.claripay.modules.charge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.charge.enums.ChargeStatusEnum;

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
@Schema(description = "Cobrança")
public class ChargeDTO {
  
  @Schema(description = "ID", example = "1")
  private Long id;
  
  @Schema(description = "Valor", example = "150.00")
  private BigDecimal amount;
  
  @Schema(description = "Descrição", example = "Consultoria Mensal")
  private String description;
  
  @Schema(description = "Data de vencimento", example = "2024-12-31")
  private LocalDate dueDate;
  
  @Schema(description = "Status")
  private ChargeStatusEnum status;
  
  @Schema(description = "Data de pagamento", example = "2024-12-15T10:30:00")
  private LocalDateTime paidAt;
  
  @Schema(description = "Código PIX", example = "00020126...")
  private String pixCode;
  
  @Schema(description = "Link de pagamento", example = "https://...")
  private String paymentLink;
  
  @Schema(description = "Ativo", example = "true")
  private Boolean active;
  
  @Schema(description = "Data de criação", example = "2024-01-01T10:00:00")
  private LocalDateTime createdAt;
  
  @Schema(description = "Data de atualização", example = "2024-01-15T14:30:00")
  private LocalDateTime updatedAt;
  
  @Schema(description = "ID do cliente", example = "1")
  private Long customerId;
  
  @Schema(description = "Nome do cliente", example = "João Silva")
  private String customerName;
  
  @Schema(description = "ID do serviço", example = "1")
  private Long serviceId;
  
  @Schema(description = "Nome do serviço", example = "Consultoria")
  private String serviceName;
}
