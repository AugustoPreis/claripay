package com.augustopreis.claripay.modules.alert.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.alert.dto.AlertDTO;
import com.augustopreis.claripay.modules.alert.mapper.AlertMapper;
import com.augustopreis.claripay.modules.alert.repository.AlertRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FindUserAlertsUseCase {

  private final AlertRepository alertRepository;
  private final AlertMapper alertMapper;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public Page<AlertDTO> execute(Boolean read, Pageable pageable) {
    Long userId = auth.getAuthenticatedId();

    if (read != null) {
      return alertRepository
          .findByUserIdAndReadOrderByCreatedAtDesc(userId, read, pageable)
          .map(alertMapper::toDTO);
    }

    return alertRepository
        .findByUserIdOrderByCreatedAtDesc(userId, pageable)
        .map(alertMapper::toDTO);
  }
}
