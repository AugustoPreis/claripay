package com.augustopreis.claripay.exception;

/**
 * Exception para recursos não encontrados
 */
public class ResourceNotFoundException extends BusinessException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
