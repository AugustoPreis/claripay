package com.augustopreis.claripay.modules.alert.controller;

import org.springdoc.core.annotations.ParameterObject;
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
import com.augustopreis.claripay.config.swagger.ApiOk;
import com.augustopreis.claripay.config.swagger.ApiUnauthorized;
import com.augustopreis.claripay.modules.alert.dto.AlertDTO;
import com.augustopreis.claripay.modules.alert.dto.UnreadCountDTO;
import com.augustopreis.claripay.modules.alert.usecase.CountUnreadAlertsUseCase;
import com.augustopreis.claripay.modules.alert.usecase.FindUserAlertsUseCase;
import com.augustopreis.claripay.modules.alert.usecase.MarkAlertAsReadUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Gerenciamento de alertas e notificações do usuário")
public class AlertController {

  private final FindUserAlertsUseCase findUserAlerts;
  private final MarkAlertAsReadUseCase markAlertAsRead;
  private final CountUnreadAlertsUseCase countUnreadAlerts;

  @GetMapping
  @Operation(summary = "Listar alertas", description = "Lista alertas do usuário com paginação e filtro")
  @ApiOk(description = "Alertas listados", schema = AlertDTO.class)
  @ApiUnauthorized(description = "Não autenticado")
  public ResponseEntity<ApiResponse<Page<AlertDTO>>> findAll(
      @Parameter(description = "Filtrar por status de leitura (true=lidos, false=não lidos, null=todos)") @RequestParam(required = false) Boolean read,
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<AlertDTO> response = findUserAlerts.execute(read, pageable);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Alertas listados com sucesso", response));
  }

  @GetMapping("/unread-count")
  @Operation(summary = "Contar não lidos", description = "Retorna quantidade de alertas não lidos")
  @ApiOk(description = "Contagem obtida", schema = UnreadCountDTO.class)
  @ApiUnauthorized(description = "Não autenticado")
  public ResponseEntity<ApiResponse<UnreadCountDTO>> getUnreadCount() {
    UnreadCountDTO response = countUnreadAlerts.execute();

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Quantidade de alertas não lidos obtida com sucesso", response));
  }

  @PatchMapping("/{id}/read")
  @Operation(summary = "Marcar como lido", description = "Marca um alerta específico como lido")
  @ApiOk(description = "Alerta marcado como lido", schema = AlertDTO.class)
  @ApiUnauthorized(description = "Não autenticado")
  public ResponseEntity<ApiResponse<AlertDTO>> markAsRead(
      @Parameter(description = "ID do alerta", example = "1") @PathVariable Long id) {
    AlertDTO response = markAlertAsRead.execute(id);

    return ResponseEntity
        .ok()
        .body(ApiResponse.success("Alerta marcado como lido", response));
  }
}
