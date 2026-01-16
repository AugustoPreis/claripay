package com.augustopreis.claripay.modules.user.dto;

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
@Schema(description = "Dados para atualizar usuário")
public class UpdateUserRequestDTO {

  @Schema(description = "Nome", example = "João Silva")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Schema(description = "E-mail", example = "joao@email.com")
  @Email(message = "E-mail inválido")
  private String email;

  @Schema(description = "Nova senha", example = "novaSenha123")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;
}
