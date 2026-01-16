package com.augustopreis.claripay.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoException extends RuntimeException {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public MercadoPagoException(String jsonContent) {
    super(parseMessage(jsonContent));
  }

  private static String parseMessage(String jsonContent) {
    try {
      MercadoPagoErrorResponse response = objectMapper.readValue(jsonContent, MercadoPagoErrorResponse.class);

      if (response.message == null) {
        return "Erro ao criar pagamento no Mercado Pago";
      }

      return response.message;
    } catch (JsonProcessingException e) {
      return "Erro ao criar pagamento no Mercado Pago";
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class MercadoPagoErrorResponse {
    public String message;
  }
}
