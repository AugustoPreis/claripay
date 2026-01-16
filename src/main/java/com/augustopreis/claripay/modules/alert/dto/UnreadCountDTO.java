package com.augustopreis.claripay.modules.alert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Contagem de alertas não lidos")
public class UnreadCountDTO {
  
  @Schema(description = "Quantidade de alertas não lidos", example = "5")
  private Long count;
}
