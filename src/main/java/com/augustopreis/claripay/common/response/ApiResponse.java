package com.augustopreis.claripay.common.response;

import lombok.*;

/**
 * Resposta padrão para sucesso da API
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

  private String message;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("Operação realizada com sucesso", data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(message, data);
  }
}
