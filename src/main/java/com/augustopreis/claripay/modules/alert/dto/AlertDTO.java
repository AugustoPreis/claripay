package com.augustopreis.claripay.modules.alert.dto;

import java.time.LocalDateTime;

import com.augustopreis.claripay.modules.alert.enums.AlertSeverityEnum;
import com.augustopreis.claripay.modules.alert.enums.AlertTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDTO {
  private Long id;
  private String title;
  private String message;
  private AlertTypeEnum type;
  private AlertSeverityEnum severity;
  private Boolean read;
  private LocalDateTime createdAt;
}
