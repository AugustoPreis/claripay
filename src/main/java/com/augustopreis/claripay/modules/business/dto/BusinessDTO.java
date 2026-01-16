package com.augustopreis.claripay.modules.business.dto;

import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.business.enums.BusinessTypeEnum;

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
@Schema(description = "Dados do negócio")
public class BusinessDTO {

  @Schema(description = "ID", example = "1")
  private Long id;

  @Schema(description = "Nome", example = "Minha Empresa LTDA")
  private String name;

  @Schema(description = "Descrição", example = "Empresa de tecnologia")
  private String description;

  @Schema(description = "Documento (CPF/CNPJ)", example = "12345678901")
  private String document;

  @Schema(description = "E-mail", example = "contato@empresa.com")
  private String email;

  @Schema(description = "Celular", example = "11999999999")
  private String cellphone;

  @Schema(description = "Tipo de negócio")
  private BusinessTypeEnum type;

  @Schema(description = "Ativo", example = "true")
  private Boolean active;

  @Schema(description = "Data de criação", example = "2026-01-16T10:30:00")
  private LocalDateTime createdAt;

  @Schema(description = "Data de atualização", example = "2026-01-16T10:30:00")
  private LocalDateTime updatedAt;
}