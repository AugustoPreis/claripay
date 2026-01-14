package com.augustopreis.claripay.modules.auth.controller;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.auth.dto.*;
import com.augustopreis.claripay.modules.auth.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final RegisterUseCase register;
  private final LoginUseCase login;
  private final ForgotPasswordUseCase forgotPassword;
  private final ResetPasswordUseCase resetPassword;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request) {
    AuthResponseDTO response = register.execute(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ApiResponse.success("Usuário registrado com sucesso", response));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
    AuthResponseDTO response = login.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Login realizado com sucesso", response));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
    forgotPassword.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Se o e-mail existir, você receberá as instruções", null));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
    resetPassword.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Senha alterada com sucesso", null));
  }
}
