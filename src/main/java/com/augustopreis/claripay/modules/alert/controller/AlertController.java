package com.augustopreis.claripay.modules.alert.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.augustopreis.claripay.common.response.ApiResponse;
import com.augustopreis.claripay.modules.alert.dto.AlertDTO;
import com.augustopreis.claripay.modules.alert.dto.UnreadCountDTO;
import com.augustopreis.claripay.modules.alert.usecase.CountUnreadAlertsUseCase;
import com.augustopreis.claripay.modules.alert.usecase.FindUserAlertsUseCase;
import com.augustopreis.claripay.modules.alert.usecase.MarkAlertAsReadUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

  private final FindUserAlertsUseCase findUserAlerts;
  private final MarkAlertAsReadUseCase markAlertAsRead;
  private final CountUnreadAlertsUseCase countUnreadAlerts;

  @GetMapping
  public ResponseEntity<ApiResponse<Page<AlertDTO>>> findAll(
      @RequestParam(required = false) Boolean read,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<AlertDTO> response = findUserAlerts.execute(read, pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Alertas listados com sucesso", response));
  }

  @GetMapping("/unread-count")
  public ResponseEntity<ApiResponse<UnreadCountDTO>> getUnreadCount() {
    UnreadCountDTO response = countUnreadAlerts.execute();

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Quantidade de alertas não lidos obtida com sucesso", response));
  }

  @PatchMapping("/{id}/read")
  public ResponseEntity<ApiResponse<AlertDTO>> markAsRead(@PathVariable Long id) {
    AlertDTO response = markAlertAsRead.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Alerta marcado como lido", response));
  }
}
