package com.augustopreis.claripay.modules.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Dados para atualizar cliente")
public class UpdateCustomerDTO {

  @Schema(description = "Nome", example = "João Silva")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Schema(description = "Documento (CPF/CNPJ)", example = "12345678901")
  @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
  private String document;

  @Schema(description = "E-mail", example = "joao@email.com")
  @Email(message = "E-mail inválido")
  @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
  private String email;

  @Schema(description = "Celular", example = "11999999999")
  @Size(max = 20, message = "O celular deve ter no máximo 20 caracteres")
  private String cellphone;
}
