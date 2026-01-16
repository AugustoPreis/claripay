package com.augustopreis.claripay.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(getApiInfo())
        .servers(getServers())
        .components(getSecurityComponents())
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }

  private Info getApiInfo() {
    return new Info()
        .title("ClariPay API")
        .version("1.0.0")
        .description("""
            API do ClariPay - Sistema de Gestão Financeira para Pequenos Negócios

            ## Sobre o Produto
            O ClariPay é um SaaS financeiro simples com foco em cobrança automática, destinado a
            pequenos empresários e profissionais autônomos (psicólogos, arquitetos, engenheiros,
            consultores, vendedores, etc.).

            ## Autenticação
            A maioria dos endpoints requer autenticação via Bearer Token (JWT).
            Use o endpoint `/api/auth/login` para obter seu token de acesso.
            """)
        .contact(new Contact()
            .name("ClariPay")
            .email("contato@claripay.com"))
        .license(new License()
            .name("Proprietary")
            .url("https://claripay.com/license"));
  }

  private List<Server> getServers() {
    return List.of(
        new Server()
            .url("http://localhost:8080")
            .description("Servidor Local de Desenvolvimento"),
        new Server()
            .url("https://api.claripay.com.br")
            .description("Servidor de Produção"));
  }

  private Components getSecurityComponents() {
    return new Components()
        .addSecuritySchemes("bearerAuth",
            new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Token JWT obtido através do endpoint de login"));
  }
}
