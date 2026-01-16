package com.augustopreis.claripay.modules.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiCreated;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.auth.dto.AuthResponseDTO;
import com.augustopreis.claripay.modules.auth.dto.ForgotPasswordRequestDTO;
import com.augustopreis.claripay.modules.auth.dto.LoginRequestDTO;
import com.augustopreis.claripay.modules.auth.dto.RegisterRequestDTO;
import com.augustopreis.claripay.modules.auth.dto.ResetPasswordRequestDTO;
import com.augustopreis.claripay.modules.auth.usecase.ForgotPasswordUseCase;
import com.augustopreis.claripay.modules.auth.usecase.GetAuthenticatedUserUseCase;
import com.augustopreis.claripay.modules.auth.usecase.LoginUseCase;
import com.augustopreis.claripay.modules.auth.usecase.RegisterUseCase;
import com.augustopreis.claripay.modules.auth.usecase.ResetPasswordUseCase;
import com.augustopreis.claripay.modules.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e recuperação de senha")
public class AuthController {

  private final RegisterUseCase register;
  private final LoginUseCase login;
  private final ForgotPasswordUseCase forgotPassword;
  private final ResetPasswordUseCase resetPassword;
  private final GetAuthenticatedUserUseCase getAuthenticatedUser;

  @PostMapping("/register")
  @Operation(summary = "Criar conta", description = "Registra novo usuário e retorna token JWT")
  @SecurityRequirements
  @ApiCreated(description = "Usuário criado", schema = AuthResponseDTO.class)
  @ApiBadRequest(description = "E-mail já existe")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
    AuthResponseDTO response = register.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Usuário registrado com sucesso", response));
  }

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Autentica usuário e retorna token JWT (válido por 24h)")
  @SecurityRequirements
  @ApiOk(description = "Login realizado", schema = AuthResponseDTO.class)
  @ApiUnauthorized(description = "Credenciais inválidas")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
    AuthResponseDTO response = login.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Login realizado com sucesso", response));
  }

  @PostMapping("/forgot-password")
  @Operation(summary = "Esqueci minha senha", description = "Envia e-mail com link para redefinir senha")
  @SecurityRequirements
  @ApiOk(description = "E-mail enviado se existir")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
    forgotPassword.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Se o e-mail existir, você receberá as instruções", null));
  }

  @PostMapping("/reset-password")
  @Operation(summary = "Redefinir senha", description = "Altera senha usando token do e-mail")
  @SecurityRequirements
  @ApiOk(description = "Senha alterada")
  @ApiBadRequest(description = "Token inválido/expirado")
  public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
    resetPassword.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Senha alterada com sucesso", null));
  }

  @GetMapping("/me")
  @Operation(summary = "Usuário autenticado", description = "Retorna dados do usuário logado")
  @ApiOk(description = "Dados do usuário", schema = UserDTO.class)
  @ApiUnauthorized(description = "Não autenticado")
  public ResponseEntity<ApiResponse<UserDTO>> me() {
    UserDTO user = getAuthenticatedUser.execute();

    return ResponseEntity.ok(ApiResponse.success("Usuário autenticado", user));
  }
}
