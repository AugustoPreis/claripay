package com.augustopreis.claripay.modules.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.auth.dto.AuthResponseDTO;
import com.augustopreis.claripay.modules.auth.dto.RegisterRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UpdateUserRequestDTO;
import com.augustopreis.claripay.modules.user.dto.UserDTO;
import com.augustopreis.claripay.modules.user.repository.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserDTO toDTO(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "resetToken", ignore = true)
  @Mapping(target = "resetTokenExpiry", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toEntity(RegisterRequestDTO request);

  @Mapping(target = "token", source = "token")
  @Mapping(target = "type", constant = "Bearer")
  @Mapping(target = "user", source = "user")
  AuthResponseDTO toAuthResponse(User user, String token);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "emailVerified", ignore = true)
  @Mapping(target = "resetToken", ignore = true)
  @Mapping(target = "resetTokenExpiry", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateUserFromDTO(UpdateUserRequestDTO dto, @MappingTarget User user);
}
