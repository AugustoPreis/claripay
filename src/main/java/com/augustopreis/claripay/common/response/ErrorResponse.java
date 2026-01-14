package com.augustopreis.claripay.common.response;

import lombok.*;

import java.util.List;

/**
 * Resposta padrão para erros da API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  private String message;
  private List<String> errors;
}
