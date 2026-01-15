CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    default_value DECIMAL(19, 2) NOT NULL,
    recurrence_type VARCHAR(50),
    recurrence_interval INTEGER,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_services_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_services_user_id ON services(user_id);
CREATE INDEX idx_services_active ON services(active);
CREATE INDEX idx_services_recurrence_type ON services(recurrence_type);

COMMENT ON TABLE services IS 'Tabela de serviços prestados pelos usuários';
COMMENT ON COLUMN services.id IS 'Identificador único do serviço';
COMMENT ON COLUMN services.name IS 'Nome do serviço';
COMMENT ON COLUMN services.description IS 'Descrição do serviço';
COMMENT ON COLUMN services.default_value IS 'Valor padrão do serviço';
COMMENT ON COLUMN services.recurrence_type IS 'Tipo de recorrência (DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM)';
COMMENT ON COLUMN services.recurrence_interval IS 'Intervalo de recorrência em dias (para tipo CUSTOM)';
COMMENT ON COLUMN services.active IS 'Indica se o serviço está ativo';
COMMENT ON COLUMN services.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN services.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN services.user_id IS 'Identificador do usuário proprietário';
