package com.augustopreis.claripay.modules.customer.dto;

import java.time.LocalDateTime;

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
@Schema(description = "Dados do cliente")
public class CustomerDTO {
  
  @Schema(description = "ID", example = "1")
  private Long id;
  
  @Schema(description = "Nome", example = "João Silva")
  private String name;
  
  @Schema(description = "Documento (CPF/CNPJ)", example = "12345678901")
  private String document;
  
  @Schema(description = "E-mail", example = "joao@email.com")
  private String email;
  
  @Schema(description = "Celular", example = "11999999999")
  private String cellphone;
  
  @Schema(description = "Ativo", example = "true")
  private Boolean active;
  
  @Schema(description = "Data de criação", example = "2026-01-16T10:30:00")
  private LocalDateTime createdAt;
  
  @Schema(description = "Data de atualização", example = "2026-01-16T10:30:00")
  private LocalDateTime updatedAt;
}
