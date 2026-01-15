package com.augustopreis.claripay.modules.withdrawal.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.withdrawal.dto.CreateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.UpdateWithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.dto.WithdrawalDTO;
import com.augustopreis.claripay.modules.withdrawal.repository.entity.Withdrawal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WithdrawalMapper {

  WithdrawalDTO toDTO(Withdrawal withdrawal);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Withdrawal toEntity(CreateWithdrawalDTO request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateWithdrawalFromDTO(UpdateWithdrawalDTO dto, @MappingTarget Withdrawal withdrawal);
}
