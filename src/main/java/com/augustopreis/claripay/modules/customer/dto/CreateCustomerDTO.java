package com.augustopreis.claripay.modules.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
  private String document;

  @Email(message = "E-mail inválido")
  @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
  private String email;

  @Size(max = 20, message = "O celular deve ter no máximo 20 caracteres")
  private String cellphone;
}
