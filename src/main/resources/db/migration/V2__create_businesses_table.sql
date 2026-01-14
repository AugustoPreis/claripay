CREATE TABLE businesses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    document VARCHAR(20),
    email VARCHAR(255),
    cellphone VARCHAR(20),
    type VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_businesses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_businesses_user_id ON businesses(user_id);
CREATE INDEX idx_businesses_type ON businesses(type);
CREATE INDEX idx_businesses_active ON businesses(active);

COMMENT ON TABLE businesses IS 'Tabela de negócios/empresas dos usuários';
COMMENT ON COLUMN businesses.id IS 'Identificador único do negócio';
COMMENT ON COLUMN businesses.name IS 'Nome do negócio';
COMMENT ON COLUMN businesses.description IS 'Descrição do negócio';
COMMENT ON COLUMN businesses.document IS 'Documento do negócio (CNPJ, CPF, etc.)';
COMMENT ON COLUMN businesses.email IS 'E-mail de contato do negócio';
COMMENT ON COLUMN businesses.cellphone IS 'Telefone de contato do negócio';
COMMENT ON COLUMN businesses.type IS 'Tipo de negócio (MEI, EIRELI, LTDA, SA)';
COMMENT ON COLUMN businesses.active IS 'Indica se o negócio está ativo';
COMMENT ON COLUMN businesses.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN businesses.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN businesses.user_id IS 'Identificador do usuário proprietário';
