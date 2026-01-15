CREATE TABLE charges (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL,
    description VARCHAR(500),
    due_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    paid_at TIMESTAMP,
    pix_code VARCHAR(500),
    payment_link VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id BIGINT NOT NULL,
    service_id BIGINT,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_charges_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    CONSTRAINT fk_charges_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL,
    CONSTRAINT fk_charges_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_charges_amount CHECK (amount > 0)
);

CREATE INDEX idx_charges_customer_id ON charges(customer_id);
CREATE INDEX idx_charges_service_id ON charges(service_id);
CREATE INDEX idx_charges_user_id ON charges(user_id);
CREATE INDEX idx_charges_status ON charges(status);
CREATE INDEX idx_charges_due_date ON charges(due_date);
CREATE INDEX idx_charges_active ON charges(active);
CREATE INDEX idx_charges_created_at ON charges(created_at);

COMMENT ON TABLE charges IS 'Tabela de cobranças geradas pelos usuários';
COMMENT ON COLUMN charges.id IS 'Identificador único da cobrança';
COMMENT ON COLUMN charges.amount IS 'Valor da cobrança';
COMMENT ON COLUMN charges.description IS 'Descrição da cobrança';
COMMENT ON COLUMN charges.due_date IS 'Data de vencimento da cobrança';
COMMENT ON COLUMN charges.status IS 'Status da cobrança (PENDING, PAID, LATE, CANCELED)';
COMMENT ON COLUMN charges.paid_at IS 'Data e hora do pagamento';
COMMENT ON COLUMN charges.pix_code IS 'Código Pix para pagamento';
COMMENT ON COLUMN charges.payment_link IS 'Link de pagamento compartilhável';
COMMENT ON COLUMN charges.active IS 'Indica se a cobrança está ativa';
COMMENT ON COLUMN charges.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN charges.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN charges.customer_id IS 'ID do cliente vinculado à cobrança';
COMMENT ON COLUMN charges.service_id IS 'ID do serviço vinculado à cobrança (opcional)';
COMMENT ON COLUMN charges.user_id IS 'ID do usuário proprietário da cobrança';
