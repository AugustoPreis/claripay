package com.augustopreis.claripay.modules.auth.dto;

import com.augustopreis.claripay.modules.user.dto.UserDTO;

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
@Schema(description = "Resposta de autenticação")
public class AuthResponseDTO {

  @Schema(description = "Token JWT", example = "eyJhbGci...")
  private String token;

  @Schema(description = "Tipo do token", example = "Bearer")
  @Builder.Default
  private String type = "Bearer";

  @Schema(description = "Dados do usuário")
  private UserDTO user;
}
