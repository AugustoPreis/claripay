package com.augustopreis.claripay.modules.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.user.dto.UpdateUserRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.usecase.UpdateUserUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UpdateUserUseCase updateUser;

  @PatchMapping("/me")
  public ResponseEntity<ApiResponse<UserDTO>> updateMe(@Valid @RequestBody UpdateUserRequestDTO request) {
    UserDTO user = updateUser.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso", user));
  }
}
