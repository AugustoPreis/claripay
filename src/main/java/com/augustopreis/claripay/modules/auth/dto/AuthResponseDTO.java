package com.augustopreis.claripay.modules.auth.dto;

import com.augustopreis.claripay.modules.user.dto.UserDTO;

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
public class AuthResponseDTO {

  private String token;

  @Builder.Default
  private String type = "Bearer";

  private UserDTO user;
}
