package com.augustopreis.claripay.modules.user.dto;

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
@Schema(description = "Dados do usuário")
public class UserDTO {

  @Schema(description = "ID", example = "1")
  private Long id;

  @Schema(description = "Nome", example = "João Silva")
  private String name;

  @Schema(description = "E-mail", example = "joao@email.com")
  private String email;

  @Schema(description = "E-mail verificado", example = "false")
  private Boolean emailVerified;
}
