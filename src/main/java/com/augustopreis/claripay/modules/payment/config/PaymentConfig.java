package com.augustopreis.claripay.modules.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;

import jakarta.annotation.PostConstruct;

@Configuration
public class PaymentConfig {

  @Value("${payment.mercadopago.access-token:}")
  private String accessToken;

  @PostConstruct
  public void init() {
    if (accessToken == null || accessToken.isEmpty()) {
      return;
    }

    MercadoPagoConfig.setAccessToken(accessToken);
  }
}
