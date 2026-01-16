package com.augustopreis.claripay.common.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.augustopreis.claripay.common.response.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Anotação customizada que agrupa as respostas padrão de erro da API.
 * 
 * Use esta anotação em endpoints para documentar automaticamente:
 * - 400 Bad Request (Validação)
 * - 401 Unauthorized (Não autenticado)
 * - 403 Forbidden (Sem permissão)
 * - 404 Not Found (Recurso não encontrado)
 * - 500 Internal Server Error (Erro interno)
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Requisição inválida - Erro de validação nos dados enviados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "401", description = "Não autenticado - Token inválido ou ausente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", description = "Acesso negado - Usuário não tem permissão para este recurso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
public @interface ApiResponsesDefault {
}
