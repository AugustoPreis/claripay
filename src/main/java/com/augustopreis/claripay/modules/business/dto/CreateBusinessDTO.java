package com.augustopreis.claripay.modules.business.dto;

import com.augustopreis.claripay.modules.business.enums.BusinessTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateBusinessDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String name;

  @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
  private String description;

  @Size(max = 20, message = "O documento deve ter no máximo 20 caracteres")
  private String document;

  @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
  private String email;

  @Size(max = 20, message = "O celular deve ter no máximo 20 caracteres")
  private String cellphone;

  @NotNull(message = "O tipo de negócio é obrigatório")
  private BusinessTypeEnum type;
}
