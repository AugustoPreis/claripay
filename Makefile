.PHONY: help build run stop clean logs test docker-build docker-up docker-down docker-logs

help:
	@echo "ClariPay - Comandos Disponíveis"
	@echo ""
	@echo "Desenvolvimento:"
	@echo "  make run          - Executa aplicação localmente (Maven)"
	@echo "  make test         - Executa testes"
	@echo "  make clean        - Limpa builds e dependências"
	@echo ""
	@echo "Docker:"
	@echo "  make docker-up    - Sobe ambiente de desenvolvimento"
	@echo "  make docker-down  - Para ambiente"
	@echo "  make docker-logs  - Visualiza logs da aplicação"
	@echo "  make docker-build - Reconstrói imagens"
	@echo ""
	@echo "Produção:"
	@echo "  make prod-up      - Sobe ambiente de produção"
	@echo "  make prod-down    - Para ambiente de produção"

run:
	./mvnw spring-boot:run

test:
	./mvnw test

clean:
	./mvnw clean

docker-build:
	docker compose build

docker-up:
	docker compose up -d
	@echo ""
	@echo "✅ Aplicação iniciada!"
	@echo "API: http://localhost:8080"
	@echo "H2 Console: http://localhost:8080/h2-console"

docker-down:
	docker compose down

docker-logs:
	docker compose logs -f app

prod-up:
	docker compose -f docker-compose.prod.yml up -d
	@echo ""
	@echo "✅ Produção iniciada!"
	@echo "API: http://localhost:8080"

prod-down:
	docker compose -f docker-compose.prod.yml down
