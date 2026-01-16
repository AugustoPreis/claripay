package com.augustopreis.claripay.modules.business.dto;

import com.augustopreis.claripay.modules.business.enums.BusinessTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criar negócio")
public class CreateBusinessDTO {

  @Schema(description = "Nome", example = "Minha Empresa LTDA")
  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Schema(description = "Descrição", example = "Empresa de tecnologia")
  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Schema(description = "Documento (CPF/CNPJ)", example = "12345678901")
  @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
  private String document;

  @Schema(description = "E-mail", example = "contato@empresa.com")
  @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
  private String email;

  @Schema(description = "Celular", example = "11999999999")
  @Size(max = 20, message = "O celular deve ter no máximo 20 caracteres")
  private String cellphone;

  @Schema(description = "Tipo de negócio")
  @NotNull(message = "O tipo de negócio é obrigatório")
  private BusinessTypeEnum type;
}
