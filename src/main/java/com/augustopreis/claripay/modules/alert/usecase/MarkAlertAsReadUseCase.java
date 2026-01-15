package com.augustopreis.claripay.modules.alert.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.exception.ResourceNotFoundException;
import com.augustopreis.claripay.modules.alert.dto.AlertDTO;
import com.augustopreis.claripay.modules.alert.mapper.AlertMapper;
import com.augustopreis.claripay.modules.alert.repository.AlertRepository;
import com.augustopreis.claripay.modules.alert.repository.entity.Alert;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarkAlertAsReadUseCase {

  private final AlertRepository alertRepository;
  private final AlertMapper alertMapper;
  private final AuthenticationUtil auth;

  @Transactional
  public AlertDTO execute(Long alertId) {
    Long userId = auth.getAuthenticatedId();

    Alert alert = alertRepository
        .findByIdAndUserId(alertId, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));

    alert.setRead(true);
    Alert updatedAlert = alertRepository.save(alert);

    return alertMapper.toDTO(updatedAlert);
  }
}
