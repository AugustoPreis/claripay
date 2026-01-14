package com.augustopreis.claripay.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "E-mail inválido")
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  private String password;
}
