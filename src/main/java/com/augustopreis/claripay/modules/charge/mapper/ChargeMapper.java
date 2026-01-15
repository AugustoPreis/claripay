package com.augustopreis.claripay.modules.charge.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.charge.dto.ChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.CreateChargeDTO;
import com.augustopreis.claripay.modules.charge.dto.UpdateChargeDTO;
import com.augustopreis.claripay.modules.charge.repository.entity.Charge;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChargeMapper {

  @Mapping(source = "customer.id", target = "customerId")
  @Mapping(source = "customer.name", target = "customerName")
  @Mapping(source = "service.id", target = "serviceId")
  @Mapping(source = "service.name", target = "serviceName")
  ChargeDTO toDTO(Charge charge);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "paidAt", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "service", ignore = true)
  @Mapping(target = "user", ignore = true)
  Charge toEntity(CreateChargeDTO request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "paidAt", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "service", ignore = true)
  @Mapping(target = "user", ignore = true)
  void updateChargeFromDTO(UpdateChargeDTO dto, @MappingTarget Charge charge);
}
