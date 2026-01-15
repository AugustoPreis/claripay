package com.augustopreis.claripay.modules.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.service.dto.CreateServiceDTO;
import com.augustopreis.claripay.modules.service.dto.ServiceDTO;
import com.augustopreis.claripay.modules.service.dto.UpdateServiceDTO;
import com.augustopreis.claripay.modules.service.repository.entity.Service;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceMapper {

  ServiceDTO toDTO(Service service);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Service toEntity(CreateServiceDTO request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateServiceFromDTO(UpdateServiceDTO dto, @MappingTarget Service service);
}
