package com.augustopreis.claripay.exception;

/**
 * Exception para erros de negócio da aplicação
 */
public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
