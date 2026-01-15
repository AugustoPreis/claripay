package com.augustopreis.claripay.modules.alert.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.alert.dto.AlertDTO;
import com.augustopreis.claripay.modules.alert.repository.entity.Alert;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlertMapper {

  AlertDTO toDTO(Alert alert);
}
