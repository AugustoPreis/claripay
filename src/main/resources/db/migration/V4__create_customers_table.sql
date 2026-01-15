CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    document VARCHAR(20),
    email VARCHAR(255),
    cellphone VARCHAR(20),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_customers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_customers_user_id ON customers(user_id);
CREATE INDEX idx_customers_active ON customers(active);
CREATE INDEX idx_customers_document ON customers(document);
CREATE INDEX idx_customers_email ON customers(email);

COMMENT ON TABLE customers IS 'Tabela de clientes dos usuários';
COMMENT ON COLUMN customers.id IS 'Identificador único do cliente';
COMMENT ON COLUMN customers.name IS 'Nome do cliente';
COMMENT ON COLUMN customers.document IS 'Documento do cliente (CPF, CNPJ, etc.)';
COMMENT ON COLUMN customers.email IS 'E-mail de contato do cliente';
COMMENT ON COLUMN customers.cellphone IS 'Telefone de contato do cliente';
COMMENT ON COLUMN customers.active IS 'Indica se o cliente está ativo';
COMMENT ON COLUMN customers.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN customers.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN customers.user_id IS 'ID do usuário proprietário do cliente';
