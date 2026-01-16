package com.augustopreis.claripay.common.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Resposta padrão para erros da API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta de erro")
public class ErrorResponse {

  @Schema(description = "Mensagem de erro")
  private String message;

  @Schema(description = "Lista de erros de validação")
  private List<String> errors;
}
