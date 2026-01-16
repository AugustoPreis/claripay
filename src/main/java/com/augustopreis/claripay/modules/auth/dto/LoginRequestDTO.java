package com.augustopreis.claripay.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Credenciais de login")
public class LoginRequestDTO {

  @Schema(description = "E-mail", example = "joao@email.com")
  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "E-mail inválido")
  private String email;

  @Schema(description = "Senha", example = "senha123")
  @NotBlank(message = "A senha é obrigatória")
  private String password;
}
