package com.augustopreis.claripay.modules.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.augustopreis.claripay.modules.auth.dto.AuthResponseDTO;
import com.augustopreis.claripay.modules.auth.dto.RegisterRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.repository.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "emailVerified", source = "emailVerified")
  UserDTO toDTO(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", constant = "true")
  @Mapping(target = "emailVerified", constant = "false")
  @Mapping(target = "resetToken", constant = "false")
  @Mapping(target = "resetTokenExpiry", constant = "false")
  @Mapping(target = "createdAt", constant = "false")
  @Mapping(target = "updatedAt", constant = "false")
  User toEntity(RegisterRequestDTO request);

  @Mapping(target = "token", source = "token")
  @Mapping(target = "type", constant = "Bearer")
  @Mapping(target = "user", source = "user")
  AuthResponseDTO toAuthResponse(User user, String token);
}
