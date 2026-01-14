package com.augustopreis.claripay.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.augustopreis.claripay.common.response.ErrorResponse;

/**
 * Handler global de exceções da aplicação
 * Padroniza respostas de erro em formato JSON
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Trata erros de validação (Bean Validation)
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getDefaultMessage());
    }

    ErrorResponse errorResponse = new ErrorResponse(
        "Erro de validação nos dados enviados",
        errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Trata erros de negócio
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        ex.getMessage(),
        List.of(ex.getMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Trata recursos não encontrados
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        ex.getMessage(),
        List.of(ex.getMessage()));

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  /**
   * Trata erros de credenciais inválidas
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        "E-mail ou senha inválidos",
        List.of("Credenciais inválidas"));

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  /**
   * Trata erros genéricos não mapeados
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        "Ocorreu um erro inesperado. Tente novamente mais tarde.",
        List.of("Erro interno do servidor"));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
