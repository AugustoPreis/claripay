package com.augustopreis.claripay.modules.expense.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.augustopreis.claripay.modules.expense.dto.CreateExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.ExpenseDTO;
import com.augustopreis.claripay.modules.expense.dto.UpdateExpenseDTO;
import com.augustopreis.claripay.modules.expense.repository.entity.Expense;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {

  ExpenseDTO toDTO(Expense expense);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Expense toEntity(CreateExpenseDTO request);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateExpenseFromDTO(UpdateExpenseDTO dto, @MappingTarget Expense expense);
}
