package com.augustopreis.claripay.modules.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.config.swagger.ApiBadRequest;
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.user.dto.UpdateUserRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.usecase.RemoveUserUseCase;
import com.augustopreis.claripay.modules.user.usecase.UpdateUserUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de dados do usuário")
public class UserController {

  private final UpdateUserUseCase updateUser;
  private final RemoveUserUseCase removeUser;

  @PatchMapping("/me")
  @Operation(summary = "Atualizar perfil", description = "Atualiza dados do usuário autenticado")
  @ApiOk(schema = UserDTO.class)
  @ApiBadRequest
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<UserDTO>> updateMe(@Valid @RequestBody UpdateUserRequestDTO request) {
    UserDTO user = updateUser.execute(request);

    return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso", user));
  }

  @DeleteMapping("/me")
  @Operation(summary = "Remover conta", description = "Remove a conta do usuário autenticado")
  @ApiOk
  @ApiUnauthorized
  public ResponseEntity<ApiResponse<Void>> removeMe() {
    removeUser.execute();

    return ResponseEntity.noContent().build();
  }
}
