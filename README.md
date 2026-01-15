# ClariPay - SaaS Financeiro

Sistema de gestão financeira para pequenos empresários e profissionais autônomos, focado em clareza, simplicidade e organização.

---

## Arquitetura

### Stack Tecnológica

- **Java 25**
- **Spring Boot 4**
- **H2 Database** (desenvolvimento - arquivo persistente)
- **PostgreSQL** (produção)
- **JWT** (autenticação stateless)
- **Flyway** (migrations)
- **JavaMailSender** (envio de e-mails)
- **Docker** (containerização)

### Padrão Arquitetural

Arquitetura modular organizada por domínios:

```
com.augustopreis.claripay/
├── config/           # Configurações gerais (CORS, JWT, Async, Database Seeder)
├── security/         # Autenticação e filtros JWT
├── exception/        # Tratamento global de erros
├── common/           # Classes compartilhadas (enums, responses, utils)
└── modules/          # Módulos de domínio
    ├── auth/         # Autenticação (login, registro, recuperação de senha)
    ├── user/         # Gestão de usuários
    ├── business/     # Gestão de negócios
    ├── service/      # Gestão de serviços
    └── email/        # Envio de e-mails transacionais
    └── [modulo]/     # Outros módulos
```

---

## Como Executar

### Pré-requisitos

- Java 25
- Maven 3.9+
- Docker e Docker Compose (opcional)

### ⚙️ Configuração Local (IMPORTANTE)

Antes de executar o projeto, você precisa criar o arquivo de configuração local:

#### 1. Criar arquivo `application-local.properties`

Crie o arquivo `src/main/resources/application-local.properties` com as seguintes configurações:

```properties
# Configurações Locais - NÃO COMMITAR ESTE ARQUIVO
# Este arquivo contém configurações sensíveis e não deve ser versionado

# Mail Configuration (exemplo com Mailtrap para desenvolvimento)
MAIL_HOST=sandbox.smtp.mailtrap.io
MAIL_PORT=2525
MAIL_USERNAME=seu-username-mailtrap
MAIL_PASSWORD=sua-senha-mailtrap

# Email Settings
EMAIL_FROM=noreply@claripay.com
EMAIL_FROM_NAME=Claripay
PASSWORD_RESET_URL=http://localhost:3000/password-reset

# JWT (opcional - já tem valor default)
# JWT_SECRET=sua-chave-secreta-personalizada
```

**Por que esse arquivo não é versionado?**

- Contém credenciais sensíveis (senhas de SMTP, secrets)
- Cada desenvolvedor pode ter configurações diferentes
- Previne vazamento de credenciais em repositórios públicos

**Para que serve?**

- Configurar servidor SMTP para envio de e-mails (recuperação de senha)
- Definir URLs do frontend para links de redirecionamento
- Sobrescrever configurações padrão sem alterar arquivos versionados

### 🔧 Desenvolvimento Local

#### Opção 1: Sem Docker

```bash
# 1. Criar o arquivo application-local.properties (veja seção acima)

# 2. Executar a aplicação (usa H2 em arquivo persistente)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Ou usando o Makefile:
make run

# 3. Acessar
# API: http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
#   - URL: jdbc:h2:file:./data/claripay
#   - Username: sa
#   - Password: (deixar vazio)
```

#### Opção 2: Com Docker

```bash
# 1. Criar o arquivo application-local.properties (veja seção acima)

# 2. Subir aplicação
docker compose up -d
# Ou: make docker-up

# 3. Ver logs
docker compose logs -f app
# Ou: make docker-logs

# 4. Reconstruir após mudanças no pom.xml
docker compose up -d --build
# Ou: make docker-build

# 5. Parar ambiente
docker compose down
# Ou: make docker-down
```

### 🐳 Produção

```bash
# 1. Configurar variáveis de ambiente
export JWT_SECRET="sua-chave-secreta-forte-aqui"
export DATABASE_URL="jdbc:postgresql://seu-host:5432/claripay"
export DATABASE_USERNAME="postgres"
export DATABASE_PASSWORD="senha-segura-do-banco"
export MAIL_HOST="smtp.seuservidor.com"
export MAIL_PORT="587"
export MAIL_USERNAME="seu-email@dominio.com"
export MAIL_PASSWORD="senha-do-email"

# 2. Subir com docker compose de produção
docker compose -f docker-compose.prod.yml up -d
# Ou make prod-up

# 3. Verificar status
docker compose -f docker-compose.prod.yml ps

# 4. Parar produção
docker compose -f docker-compose.prod.yml down
# Ou make prod-down
```

---

## Autenticação

O sistema usa **JWT stateless**.

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
      "id": 1,
      "name": "Admin",
      "email": "admin@gmail.com"
    }
  }
}
```

### Usando o Token

Inclua o token no header das requisições protegidas:

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer seu-token-jwt-aqui"
```

---

## Estrutura de Respostas

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

## Migrations

As migrations são gerenciadas automaticamente pelo Flyway e ficam em:

`src/main/resources/db/migration/`

O Flyway executa automaticamente na inicialização da aplicação.

---

## Docker

### Dockerfiles

- `Dockerfile` - Multi-stage build para produção
- `Dockerfile.dev` - Imagem para desenvolvimento

### Docker Compose

- `docker-compose.yml` - Desenvolvimento
- `docker-compose.prod.yml` - Produção

---

## Monitoramento

Spring Boot Actuator habilitado:

- **Health Check**: http://localhost:8080/actuator/health - Status da aplicação
- **Info**: http://localhost:8080/actuator/info - Informações da aplicação
- **Metrics**: http://localhost:8080/actuator/metrics - Métricas (apenas em desenvolvimento)
