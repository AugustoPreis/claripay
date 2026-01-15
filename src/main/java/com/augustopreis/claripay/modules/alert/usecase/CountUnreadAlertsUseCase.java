package com.augustopreis.claripay.modules.alert.usecase;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augustopreis.claripay.common.util.AuthenticationUtil;
import com.augustopreis.claripay.modules.alert.dto.UnreadCountDTO;
import com.augustopreis.claripay.modules.alert.repository.AlertRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountUnreadAlertsUseCase {

  private final AlertRepository alertRepository;
  private final AuthenticationUtil auth;

  @Transactional(readOnly = true)
  public UnreadCountDTO execute() {
    Long userId = auth.getAuthenticatedId();
    Long count = alertRepository.countByUserIdAndRead(userId, false);

    return UnreadCountDTO.builder()
        .count(count)
        .build();
  }
}
