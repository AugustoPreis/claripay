package com.augustopreis.claripay.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.augustopreis.claripay.common.response.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
    responseCode = "400",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface ApiBadRequest {
  String description() default "Requisição inválida - Erro de validação nos dados enviados";
}