package com.augustopreis.claripay.modules.business.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.business.dto.BusinessDTO;
import com.augustopreis.claripay.modules.business.dto.CreateBusinessDTO;
import com.augustopreis.claripay.modules.business.dto.UpdateBusinessDTO;
import com.augustopreis.claripay.modules.business.repository.entity.Business;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusinessMapper {

  BusinessDTO toDTO(Business business);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Business toEntity(CreateBusinessDTO request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateBusinessFromDTO(UpdateBusinessDTO dto, @MappingTarget Business business);
}
