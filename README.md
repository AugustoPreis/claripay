# ClariPay - SaaS Financeiro

Sistema de gestão financeira para pequenos empresários e profissionais autônomos, focado em clareza, simplicidade e cobrança automática.

---

## Arquitetura

### Stack Tecnológica

- **Java 25**
- **Spring Boot 4**
- **PostgreSQL** (produção) / **H2** (desenvolvimento)
- **JWT** (autenticação stateless)
- **Flyway** (migrations)
- **Docker** (containerização)

### Padrão Arquitetural

Arquitetura modular

```
com.augustopreis.claripay/
├── config/           # Configurações gerais (CORS, JWT)
├── security/         # Autenticação e filtros JWT
├── exception/        # Tratamento global de erros
├── common/           # Classes compartilhadas (enums, responses)
└── modules/          # Módulos de domínio
    └── [modulo]/         # Nome do módulo
        ├── [item]/       # Pastas pertencentes ao módulo. controllers, usecases, repositories, ...etc
```

---

## Como Executar

### Pré-requisitos

- Java 25
- Maven 3.9+
- Docker e Docker Compose (opcional)

### Desenvolvimento Local

#### Sem Docker

```bash
# 1. Executar a aplicação (usa H2 em memória)
./mvnw spring-boot:run

# 2. Acessar
# API: http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
```

#### Com Docker (Hot-reload)

```bash
# 1. Subir aplicação + PostgreSQL
docker compose up

# 2. Reconstruir após mudanças no pom.xml
docker compose up --build

# 3. Logs
docker compose logs -f app
```

### Produção

```bash
# 1. Configurar variáveis de ambiente
export JWT_SECRET="seu-secret-aqui"
export DB_PASSWORD="senha-segura"

# 2. Subir com docker compose de produção
docker compose -f docker-compose.prod.yml up -d

# 3. Verificar status
docker compose -f docker-compose.prod.yml ps
```

---

## Autenticação

O sistema usa **JWT stateless**. Endpoints públicos:

- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /actuator/health`

### Exemplo de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@gmail.com", "password": "admin123"}'
```

Resposta:

```json
{
  "message": "Login realizado com sucesso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "user": {
      /* Dados do usuário */
    }
  }
}
```

---

## 📦 Estrutura de Respostas

### Sucesso

```json
{
  "message": "Operação realizada com sucesso",
  "data": { ... }
}
```

### Erro

```json
{
  "message": "Mensagem principal do erro",
  "errors": ["Erro específico 1", "Erro específico 2"]
}
```

---

## 🗄️ Banco de Dados

### Desenvolvimento (H2)

- URL: `jdbc:h2:mem:claripay`
- Console: http://localhost:8080/h2-console
- Usuário: `sa`
- Senha: _(vazio)_

### Produção (PostgreSQL)

Configurado via variáveis de ambiente:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/claripay
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=sua-senha
```

### Migrations

Gerenciadas pelo Flyway em `src/main/resources/db/migration/`

---

## 🐳 Docker

### Dockerfiles

- `Dockerfile` - Multi-stage build para produção
- `Dockerfile.dev` - Imagem para desenvolvimento com hot-reload

### Docker Compose

- `docker-compose.yml` - Desenvolvimento (hot-reload habilitado)
- `docker-compose.prod.yml` - Produção (otimizado)

### Comandos Úteis

```bash
# Desenvolvimento
docker compose up          # Subir aplicação
docker compose down        # Parar aplicação
docker compose logs -f app # Ver logs

# Produção
docker compose -f docker-compose.prod.yml up -d
docker compose -f docker-compose.prod.yml down

# Limpar volumes
docker compose down -v
```

---

## 🔍 Monitoramento

Spring Boot Actuator habilitado:

- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/info
- http://localhost:8080/actuator/metrics (Indisponível em produção)
