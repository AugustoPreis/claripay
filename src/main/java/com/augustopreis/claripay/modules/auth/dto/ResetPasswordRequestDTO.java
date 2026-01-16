package com.augustopreis.claripay.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para redefinir senha")
public class ResetPasswordRequestDTO {

  @Schema(description = "Token recebido por e-mail", example = "abc123xyz")
  @NotBlank(message = "O token é obrigatório")
  private String token;

  @Schema(description = "Nova senha", example = "novaSenha123")
  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String newPassword;
}
